package com.erp.trillion.feature.inventory.roll

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.erp.trillion.core.base.circuit.showInDialog
import com.erp.trillion.core.base.circuit.wrapEventSink
import com.erp.trillion.core.domain.model.FabricRoll
import com.erp.trillion.core.domain.repository.RollInventoryRepository
import com.erp.trillion.core.navigation.ReleaseHistoryScreen
import com.erp.trillion.core.navigation.RollFormScreen
import com.erp.trillion.core.navigation.RollInventoryScreen
import com.erp.trillion.core.ui.model.toTableItem
import com.slack.circuit.overlay.LocalOverlayHost
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class RollInventoryPresenterFactory(
    private val presenterFactory: (Navigator, Long) -> RollInventoryPresenter,
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext
    ): Presenter<*>? {
        return when (screen) {
            is RollInventoryScreen -> presenterFactory(navigator, screen.zoneId)
            else -> null
        }
    }
}

@Inject
class RollInventoryPresenter(
    @Assisted private val navigator: Navigator,
    @Assisted private val zoneId: Long,
    private val rollInventoryRepository: RollInventoryRepository,
) : Presenter<RollInventoryUiState> {

    @Composable
    override fun present(): RollInventoryUiState {
        val overlayHost = LocalOverlayHost.current
        var currentPage by rememberRetained { mutableStateOf(0) }

        val rollTable: com.erp.trillion.core.ui.model.RollTable? by rememberRetained {
            snapshotFlow { currentPage }
                .distinctUntilChanged()
                .flatMapLatest {
                    rollInventoryRepository.getFabricRolls(
                        zoneId = zoneId,
                        page = it,
                        pageSize = PAGE_SIZE,
                    )
                }
                .map { rolls ->
                    com.erp.trillion.core.ui.model.RollTable(
                        items = rolls.map(
                            FabricRoll::toTableItem
                        )
                    )
                }
        }.collectAsRetainedState(null)

        val eventSink: CoroutineScope.(RollInventoryEvent) -> Unit = { event ->
            when (event) {
                RollInventoryEvent.NavigateUp -> navigator.pop()

                is RollInventoryEvent.ShowRollDetail -> {
                    navigator.goTo(ReleaseHistoryScreen(rollId = event.rollId))
                }

                is RollInventoryEvent.EditRoll -> {
                    launch {
                        overlayHost.showInDialog(
                            RollFormScreen(event.rollId),
                            navigator::goTo
                        )
                    }
                }

                is RollInventoryEvent.DeleteRoll -> {
                    launch {
                        try {
                            rollInventoryRepository.removeFabricRoll(event.rollId)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                RollInventoryEvent.ShowNextPage -> {
                    if (rollTable!!.items.size == PAGE_SIZE) {
                        currentPage += 1
                    }
                }

                RollInventoryEvent.ShowPreviousPage -> {
                    currentPage = (currentPage - 1).coerceAtLeast(0)
                }
            }
        }

        return when {
            rollTable == null -> RollInventoryUiState.Loading(wrapEventSink(eventSink))

            else -> RollInventoryUiState.Rolls(
                rollTable = rollTable!!,
                eventSink = wrapEventSink(eventSink),
            )
        }
    }
}

private const val PAGE_SIZE = 20

sealed interface RollInventoryUiState : CircuitUiState {
    val eventSink: (RollInventoryEvent) -> Unit

    data class Loading(
        override val eventSink: (RollInventoryEvent) -> Unit = {},
    ) : RollInventoryUiState

    data class Rolls(
        val rollTable: com.erp.trillion.core.ui.model.RollTable,
        override val eventSink: (RollInventoryEvent) -> Unit = {},
    ) : RollInventoryUiState
}

sealed interface RollInventoryEvent : CircuitUiEvent {
    data object NavigateUp : RollInventoryEvent
    data class ShowRollDetail(val rollId: Long) : RollInventoryEvent
    data class EditRoll(val rollId: Long) : RollInventoryEvent
    data class DeleteRoll(val rollId: Long) : RollInventoryEvent
    data object ShowPreviousPage : RollInventoryEvent
    data object ShowNextPage : RollInventoryEvent
}
