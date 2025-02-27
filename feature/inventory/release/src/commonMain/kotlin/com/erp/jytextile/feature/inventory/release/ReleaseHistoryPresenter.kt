package com.erp.jytextile.feature.inventory.release

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.erp.jytextile.core.base.circuit.wrapEventSink
import com.erp.jytextile.core.domain.model.ReleaseHistory
import com.erp.jytextile.core.domain.repository.ReleaseHistoryRepository
import com.erp.jytextile.core.navigation.ReleaseHistoryScreen
import com.erp.jytextile.core.ui.model.ReleaseHistoryTable
import com.erp.jytextile.core.ui.model.TableItem
import com.erp.jytextile.core.ui.model.toTableItem
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
        var selectedRows by rememberRetained { mutableStateOf(emptySet<TableItem>()) }

        val eventSink: CoroutineScope.(ReleaseHistoryEvent) -> Unit = { event ->
            when (event) {
                ReleaseHistoryEvent.Back -> navigator.pop()

                ReleaseHistoryEvent.Remove -> {
                    launch {
                        try {
                            releaseHistoryRepository.removeReleaseHistories(
                                selectedRows.map { it.id }.toList()
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                is ReleaseHistoryEvent.SelectRow -> {
                    if (selectedRows.contains(event.row)) {
                        selectedRows = selectedRows - event.row
                    } else {
                        selectedRows = selectedRows + event.row
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
                selectedRows = selectedRows.toList(),
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
        val selectedRows: List<TableItem>,
        override val eventSink: (ReleaseHistoryEvent) -> Unit = {},
    ) : ReleaseHistoryUiState
}

sealed interface ReleaseHistoryEvent : CircuitUiEvent {
    data object Back : ReleaseHistoryEvent
    data object Remove : ReleaseHistoryEvent
    data class SelectRow(
        val row: TableItem
    ) : ReleaseHistoryEvent
}
