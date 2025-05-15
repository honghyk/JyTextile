package com.erp.trillion.feature.inventory.release

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.erp.trillion.core.base.circuit.wrapEventSink
import com.erp.trillion.core.domain.model.ReleaseHistory
import com.erp.trillion.core.domain.repository.ReleaseHistoryRepository
import com.erp.trillion.core.navigation.ReleaseHistoryScreen
import com.erp.trillion.core.ui.model.ReleaseHistoryTable
import com.erp.trillion.core.ui.model.TableItem
import com.erp.trillion.core.ui.model.toTableItem
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class ReleaseHistoryPresenterFactory(
    private val presenterFactory: (Navigator, Long) -> ReleaseHistoryPresenter,
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext
    ): Presenter<*>? {
        return when (screen) {
            is ReleaseHistoryScreen -> presenterFactory(navigator, screen.rollId)
            else -> null
        }
    }
}

@Inject
class ReleaseHistoryPresenter(
    @Assisted private val navigator: Navigator,
    @Assisted private val rollId: Long,
    private val releaseHistoryRepository: ReleaseHistoryRepository,
) : Presenter<ReleaseHistoryUiState> {

    @Composable
    override fun present(): ReleaseHistoryUiState {
        val releaseHistoryTable by rememberRetained(rollId) {
            releaseHistoryRepository.getReleaseHistories(rollId).map { histories ->
                ReleaseHistoryTable(
                    items = histories.map(ReleaseHistory::toTableItem),
                )
            }
        }.collectAsRetainedState(null)

        val eventSink: CoroutineScope.(ReleaseHistoryEvent) -> Unit = { event ->
            when (event) {
                ReleaseHistoryEvent.NavigateUp -> navigator.pop()

                is ReleaseHistoryEvent.DeleteHistory -> {
                    launch {
                        try {
                            releaseHistoryRepository.removeReleaseHistories(listOf(event.item.id))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }

        return when {
            releaseHistoryTable == null -> ReleaseHistoryUiState.Loading(
                eventSink = wrapEventSink(eventSink),
            )

            else -> ReleaseHistoryUiState.ReleaseHistories(
                rollId = rollId,
                releaseHistoryTable = releaseHistoryTable!!,
                eventSink = wrapEventSink(eventSink),
            )
        }
    }
}

sealed interface ReleaseHistoryUiState : CircuitUiState {
    val eventSink: (ReleaseHistoryEvent) -> Unit

    data class Loading(
        override val eventSink: (ReleaseHistoryEvent) -> Unit = {},
    ) : ReleaseHistoryUiState

    data class ReleaseHistories(
        val rollId: Long,
        val releaseHistoryTable: ReleaseHistoryTable,
        override val eventSink: (ReleaseHistoryEvent) -> Unit = {},
    ) : ReleaseHistoryUiState
}

sealed interface ReleaseHistoryEvent : CircuitUiEvent {
    data object NavigateUp : ReleaseHistoryEvent
    data class DeleteHistory(val item: TableItem) : ReleaseHistoryEvent
}
