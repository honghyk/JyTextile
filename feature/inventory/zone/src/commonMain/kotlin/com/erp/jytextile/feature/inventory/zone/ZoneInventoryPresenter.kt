package com.erp.jytextile.feature.inventory.zone

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.erp.jytextile.core.base.circuit.showInDialog
import com.erp.jytextile.core.domain.model.Zone
import com.erp.jytextile.core.domain.repository.ZoneInventoryRepository
import com.erp.jytextile.core.navigation.RollFormScreen
import com.erp.jytextile.core.navigation.RollInventoryScreen
import com.erp.jytextile.core.navigation.ZoneFormScreen
import com.erp.jytextile.core.navigation.ZoneInventoryScreen
import com.erp.jytextile.core.ui.model.TableItem
import com.erp.jytextile.core.ui.model.ZoneTable
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
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class ZoneInventoryPresenterFactory(
    private val presenterFactory: (Navigator) -> ZoneInventoryPresenter,
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext
    ): Presenter<*>? {
        return when (screen) {
            ZoneInventoryScreen -> presenterFactory(navigator)
            else -> null
        }
    }
}

@Inject
class ZoneInventoryPresenter(
    @Assisted private val navigator: Navigator,
    private val inventoryRepository: ZoneInventoryRepository,
) : Presenter<ZoneInventoryUiState> {

    @Composable
    override fun present(): ZoneInventoryUiState {
        val scope = rememberCoroutineScope()
        val overlayHost = LocalOverlayHost.current

        var currentPage by rememberRetained { mutableStateOf(0) }
        var selectedRows by rememberRetained(currentPage) { mutableStateOf(emptySet<TableItem>()) }

        val zoneTable by rememberRetained {
            snapshotFlow { currentPage }
                .flatMapLatest { inventoryRepository.getZones(it, pageSize = PAGE_SIZE) }
                .mapLatest { zones -> zones.map(Zone::toTableItem) }
                .map {
                    ZoneTable(
                        items = it,
                        currentPage = currentPage,
                        totalPage = -1
                    )
                }
        }.collectAsRetainedState(null)

        return when {
            zoneTable == null -> ZoneInventoryUiState.Loading

            else -> ZoneInventoryUiState.Zones(
                zoneTable = zoneTable!!,
                selectedRows = selectedRows.toList(),
            ) { event ->
                when (event) {
                    is ZoneInventoryEvent.ToRolls -> {
                        navigator.goTo(
                            screen = RollInventoryScreen(event.zoneId)
                        )
                    }

                    is ZoneInventoryEvent.SelectRow -> {
                        if (selectedRows.contains(event.row)) {
                            selectedRows = selectedRows - event.row
                        } else {
                            selectedRows = selectedRows + event.row
                        }
                    }

                    ZoneInventoryEvent.AddZone -> {
                        scope.launch {
                            overlayHost.showInDialog(
                                screen = ZoneFormScreen,
                                onGoToScreen = navigator::goTo
                            )
                        }
                    }

                    ZoneInventoryEvent.RemoveZone -> {
                        scope.launch {
                            try {
                                inventoryRepository.deleteZones(selectedRows.map { it.id })
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            selectedRows = emptySet()
                        }
                    }

                    ZoneInventoryEvent.AddRoll -> {
                        scope.launch {
                            overlayHost.showInDialog(
                                screen = RollFormScreen(null),
                                onGoToScreen = navigator::goTo
                            )
                        }
                    }

                    ZoneInventoryEvent.NextPage -> {
                        if (zoneTable!!.items.size == PAGE_SIZE) {
                            currentPage += 1
                        }
                    }

                    ZoneInventoryEvent.PreviousPage -> {
                        currentPage = (currentPage - 1).coerceAtLeast(0)
                    }
                }
            }
        }
    }
}

private const val PAGE_SIZE = 20

sealed interface ZoneInventoryUiState : CircuitUiState {

    data object Loading : ZoneInventoryUiState

    data class Zones(
        val zoneTable: ZoneTable,
        val selectedRows: List<TableItem>,
        val eventSink: (ZoneInventoryEvent) -> Unit = {},
    ) : ZoneInventoryUiState
}

sealed interface ZoneInventoryEvent : CircuitUiEvent {
    data class ToRolls(val zoneId: Long) : ZoneInventoryEvent
    data object AddZone : ZoneInventoryEvent
    data object RemoveZone : ZoneInventoryEvent
    data object AddRoll : ZoneInventoryEvent
    data class SelectRow(val row: TableItem) : ZoneInventoryEvent
    data object PreviousPage : ZoneInventoryEvent
    data object NextPage : ZoneInventoryEvent
}
