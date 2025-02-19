package com.erp.jytextile.feature.inventory.roll

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.erp.jytextile.core.base.circuit.showInDialog
import com.erp.jytextile.core.base.circuit.wrapEventSink
import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.core.domain.repository.InventoryRepository
import com.erp.jytextile.core.navigation.ReleaseFormScreen
import com.erp.jytextile.core.navigation.ReleaseHistoryScreen
import com.erp.jytextile.core.navigation.RollInventoryScreen
import com.erp.jytextile.core.ui.model.RollTable
import com.erp.jytextile.core.ui.model.toTableItem
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
import kotlinx.coroutines.flow.flowOf
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
    private val inventoryRepository: InventoryRepository,
) : Presenter<RollInventoryUiState> {

    @Composable
    override fun present(): RollInventoryUiState {
        val overlayHost = LocalOverlayHost.current

        val rollCount by inventoryRepository.getFabricRollsCount(zoneId).collectAsRetainedState(0)

        var currentPage by rememberRetained { mutableStateOf(0) }
        val totalPage by inventoryRepository.getFabricRollsPage(zoneId).collectAsRetainedState(0)

        val rollTable by rememberRetained(currentPage) {
            inventoryRepository.getFabricRolls(zoneId, currentPage, false).map { rolls ->
                RollTable(
                    items = rolls.map(FabricRoll::toTableItem)
                )
            }
        }.collectAsRetainedState(null)

        var selectedRollId: Long? by rememberRetained(rollTable) { mutableStateOf(rollTable?.items?.firstOrNull()?.id) }
        val selectedRoll by rememberRetained(selectedRollId) {
            if (selectedRollId == null) {
                flowOf(null)
            } else {
                inventoryRepository.getRoll(selectedRollId!!)
            }
        }.collectAsRetainedState(null)

        val eventSink: CoroutineScope.(RollInventoryEvent) -> Unit = { event ->
            when (event) {
                com.erp.jytextile.feature.inventory.roll.RollInventoryEvent.Back -> navigator.pop()

                is RollInventoryEvent.RollSelected -> selectedRollId = event.rollId

                com.erp.jytextile.feature.inventory.roll.RollInventoryEvent.NextPage -> {
                    currentPage = (currentPage + 1).coerceAtMost(totalPage - 1)
                }

                com.erp.jytextile.feature.inventory.roll.RollInventoryEvent.PreviousPage -> {
                    currentPage = (currentPage - 1).coerceAtLeast(0)
                }

                com.erp.jytextile.feature.inventory.roll.RollInventoryEvent.Remove -> {
                    launch {
                        selectedRollId?.let { rollId ->
                            inventoryRepository.removeFabricRoll(rollId)
                        }
                    }
                }

                com.erp.jytextile.feature.inventory.roll.RollInventoryEvent.Release -> {
                    launch {
                        selectedRoll?.let {
                            overlayHost.showInDialog(
                                ReleaseFormScreen(it.id, it.itemNo),
                                navigator::goTo
                            )
                        }
                    }
                }

                com.erp.jytextile.feature.inventory.roll.RollInventoryEvent.ReleaseHistory -> {
                    selectedRoll?.let { roll ->
                        navigator.goTo(ReleaseHistoryScreen(roll.id, roll.itemNo))
                    }
                }
            }
        }

        return when {
            rollTable == null -> RollInventoryUiState.Loading(wrapEventSink(eventSink))

            else -> RollInventoryUiState.Rolls(
                rollTable = rollTable!!,
                rollCount = rollCount,
                currentPage = currentPage + 1,
                totalPage = totalPage,
                selectedRoll = selectedRoll,
                eventSink = wrapEventSink(eventSink),
            )
        }
    }
}

sealed interface RollInventoryUiState : CircuitUiState {
    val eventSink: (RollInventoryEvent) -> Unit

    data class Loading(
        override val eventSink: (RollInventoryEvent) -> Unit = {},
    ) : RollInventoryUiState

    data class Rolls(
        val rollTable: RollTable,
        val rollCount: Int,
        val currentPage: Int,
        val totalPage: Int,
        val selectedRoll: FabricRoll?,
        override val eventSink: (RollInventoryEvent) -> Unit = {},
    ) : RollInventoryUiState
}

sealed interface RollInventoryEvent : CircuitUiEvent {
    data object Back : RollInventoryEvent
    data class RollSelected(val rollId: Long) : RollInventoryEvent
    data object PreviousPage : RollInventoryEvent
    data object NextPage : RollInventoryEvent
    data object Remove : RollInventoryEvent
    data object Release : RollInventoryEvent
    data object ReleaseHistory : RollInventoryEvent
}
