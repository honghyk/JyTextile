package com.erp.jytextile.feature.inventory.roll

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.erp.jytextile.core.base.circuit.wrapEventSink
import com.erp.jytextile.core.base.parcel.Parcelize
import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.core.domain.repository.InventoryRepository
import com.erp.jytextile.feature.inventory.roll.model.RollTable
import com.erp.jytextile.feature.inventory.roll.model.toTableItem
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

@Parcelize
data class RollInventoryScreen(
    val zoneId: Long,
) : Screen

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
                RollInventoryEvent.Back -> navigator.pop()

                is RollInventoryEvent.RollSelected -> selectedRollId = event.rollId

                RollInventoryEvent.NextPage -> {
                    currentPage = (currentPage + 1).coerceAtMost(totalPage - 1)
                }

                RollInventoryEvent.PreviousPage -> {
                    currentPage = (currentPage - 1).coerceAtLeast(0)
                }

                RollInventoryEvent.Remove -> {
                    launch {
                        selectedRollId?.let { rollId ->
                            inventoryRepository.removeFabricRoll(rollId)
                        }
                    }
                }

                RollInventoryEvent.Release -> { /* TODO */ }
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
}
