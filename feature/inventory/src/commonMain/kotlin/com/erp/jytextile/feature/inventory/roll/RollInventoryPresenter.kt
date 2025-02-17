package com.erp.jytextile.feature.inventory.roll

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.flow.map
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

        return when {
            rollTable == null -> RollInventoryUiState.Loading

            else -> RollInventoryUiState.Rolls(
                rollTable = rollTable!!,
                rollCount = rollCount,
                currentPage = currentPage + 1,
                totalPage = totalPage
            ) { event ->
                when (event) {
                    RollInventoryEvent.NextPage -> {
                        currentPage = (currentPage + 1).coerceAtMost(totalPage - 1)
                    }

                    RollInventoryEvent.PreviousPage -> {
                        currentPage = (currentPage - 1).coerceAtLeast(0)
                    }
                }
            }
        }
    }
}

sealed interface RollInventoryUiState : CircuitUiState {

    data object Loading : RollInventoryUiState

    data class Rolls(
        val rollTable: RollTable,
        val rollCount: Int,
        val currentPage: Int,
        val totalPage: Int,
        val eventSink: (RollInventoryEvent) -> Unit = {},
    ) : RollInventoryUiState
}

sealed interface RollInventoryEvent : CircuitUiEvent {
    data object PreviousPage : RollInventoryEvent
    data object NextPage : RollInventoryEvent
}
