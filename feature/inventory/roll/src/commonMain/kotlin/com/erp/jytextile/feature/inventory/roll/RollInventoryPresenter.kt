package com.erp.jytextile.feature.inventory.roll

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.erp.jytextile.core.base.circuit.wrapEventSink
import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.core.domain.repository.RollInventoryRepository
import com.erp.jytextile.core.navigation.ReleaseHistoryScreen
import com.erp.jytextile.core.navigation.RollInventoryScreen
import com.erp.jytextile.core.ui.model.RollTable
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
        val rollCount by rollInventoryRepository.getFabricRollsCount(zoneId)
            .collectAsRetainedState(0)

        var currentPage by rememberRetained { mutableStateOf(0) }
        val totalPage by rollInventoryRepository.getFabricRollsPage(zoneId)
            .collectAsRetainedState(0)

        val rollTable by rememberRetained(currentPage) {
            rollInventoryRepository.getFabricRolls(zoneId, currentPage, false).map { rolls ->
                RollTable(
                    items = rolls.map(FabricRoll::toTableItem)
                )
            }
        }.collectAsRetainedState(null)

        val eventSink: CoroutineScope.(RollInventoryEvent) -> Unit = { event ->
            when (event) {
                RollInventoryEvent.Back -> navigator.pop()

                is RollInventoryEvent.RollSelected -> {
                    navigator.goTo(ReleaseHistoryScreen(rollId = event.rollId))
                }

                RollInventoryEvent.NextPage -> {
                    currentPage = (currentPage + 1).coerceAtMost(totalPage - 1)
                }

                RollInventoryEvent.PreviousPage -> {
                    currentPage = (currentPage - 1).coerceAtLeast(0)
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
        override val eventSink: (RollInventoryEvent) -> Unit = {},
    ) : RollInventoryUiState
}

sealed interface RollInventoryEvent : CircuitUiEvent {
    data object Back : RollInventoryEvent
    data class RollSelected(val rollId: Long) : RollInventoryEvent
    data object PreviousPage : RollInventoryEvent
    data object NextPage : RollInventoryEvent
}
