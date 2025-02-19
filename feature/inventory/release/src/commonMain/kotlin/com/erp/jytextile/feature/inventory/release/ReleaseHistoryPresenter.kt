package com.erp.jytextile.feature.inventory.release

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.erp.jytextile.core.domain.model.ReleaseHistory
import com.erp.jytextile.core.domain.repository.ReleaseHistoryRepository
import com.erp.jytextile.core.navigation.ReleaseHistoryScreen
import com.erp.jytextile.core.ui.model.ReleaseHistoryTable
import com.erp.jytextile.core.ui.model.toTableItem
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class ReleaseHistoryPresenterFactory(
    private val presenterFactory: (Navigator, Long, String) -> ReleaseHistoryPresenter,
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext
    ): Presenter<*>? {
        return when (screen) {
            is ReleaseHistoryScreen -> presenterFactory(navigator, screen.rollId, screen.rollItemNo)
            else -> null
        }
    }
}

@Inject
class ReleaseHistoryPresenter(
    @Assisted private val navigator: Navigator,
    @Assisted private val rollId: Long,
    @Assisted private val rollItemNo: String,
    private val releaseHistoryRepository: ReleaseHistoryRepository,
) : Presenter<ReleaseHistoryUiState> {

    @Composable
    override fun present(): ReleaseHistoryUiState {
        var currentPage by rememberRetained { mutableStateOf(0) }
        val totalPage by releaseHistoryRepository.getReleaseHistoriesPage(rollId)
            .collectAsRetainedState(0)

        val releaseHistoryTable by rememberRetained(currentPage) {
            releaseHistoryRepository.getReleaseHistories(rollId, currentPage).map { histories ->
                ReleaseHistoryTable(
                    items = histories.map(ReleaseHistory::toTableItem)
                )
            }
        }.collectAsRetainedState(null)

        val eventSink: (ReleaseHistoryEvent) -> Unit = { event ->
            when (event) {
                ReleaseHistoryEvent.Back -> navigator.pop()
                ReleaseHistoryEvent.NextPage -> {
                    currentPage = (currentPage + 1).coerceAtMost(totalPage - 1)
                }

                ReleaseHistoryEvent.PreviousPage -> {
                    currentPage = (currentPage - 1).coerceAtLeast(0)
                }

                ReleaseHistoryEvent.Remove -> {
                    TODO("Not yet implemented")
                }
            }
        }

        return when {
            releaseHistoryTable == null -> ReleaseHistoryUiState.Loading(
                title = "(no.$rollId) $rollItemNo",
                eventSink = eventSink,
            )

            else -> ReleaseHistoryUiState.ReleaseHistories(
                title = "($rollId) $rollItemNo",
                releaseHistoryTable = releaseHistoryTable!!,
                currentPage = currentPage + 1,
                totalPage = totalPage,
                eventSink = eventSink,
            )
        }
    }
}

sealed interface ReleaseHistoryUiState : CircuitUiState {
    val title: String
    val eventSink: (ReleaseHistoryEvent) -> Unit

    data class Loading(
        override val title: String,
        override val eventSink: (ReleaseHistoryEvent) -> Unit = {},
    ) : ReleaseHistoryUiState

    data class ReleaseHistories(
        override val title: String,
        val releaseHistoryTable: ReleaseHistoryTable,
        val currentPage: Int,
        val totalPage: Int,
        override val eventSink: (ReleaseHistoryEvent) -> Unit = {},
    ) : ReleaseHistoryUiState
}

sealed interface ReleaseHistoryEvent : CircuitUiEvent {
    data object Back : ReleaseHistoryEvent
    data object PreviousPage : ReleaseHistoryEvent
    data object NextPage : ReleaseHistoryEvent
    data object Remove : ReleaseHistoryEvent
}
