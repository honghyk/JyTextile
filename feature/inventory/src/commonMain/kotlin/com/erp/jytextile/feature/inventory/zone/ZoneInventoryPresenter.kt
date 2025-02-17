package com.erp.jytextile.feature.inventory.zone

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.erp.jytextile.core.base.circuit.showInDialog
import com.erp.jytextile.core.base.parcel.Parcelize
import com.erp.jytextile.core.domain.model.Section
import com.erp.jytextile.core.domain.repository.InventoryRepository
import com.erp.jytextile.feature.inventory.zone.model.ZoneTable
import com.erp.jytextile.feature.inventory.zone.model.toTableItem
import com.slack.circuit.overlay.LocalOverlayHost
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.screen.StaticScreen
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Parcelize
data object ZoneInventoryScreen : StaticScreen

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
    private val inventoryRepository: InventoryRepository,
) : Presenter<ZoneInventoryUiState> {

    @Composable
    override fun present(): ZoneInventoryUiState {
        val scope = rememberCoroutineScope()
        val overlayHost = LocalOverlayHost.current

        val zonesCount by inventoryRepository.getSectionsCount().collectAsRetainedState(0)

        var currentPage by rememberRetained { mutableStateOf(0) }
        val sectionPages by inventoryRepository.getSectionPages().collectAsRetainedState(0)

        val zoneTable by rememberRetained(currentPage) {
            inventoryRepository.getSections(currentPage).map { sections ->
                ZoneTable(
                    items = sections.map(Section::toTableItem)
                )
            }
        }.collectAsRetainedState(null)

        return when {
            zoneTable == null -> ZoneInventoryUiState.Loading

            else -> ZoneInventoryUiState.Zones(
                zoneTable = zoneTable!!,
                zonesCount = zonesCount,
                currentPage = currentPage + 1,
                totalPage = sectionPages
            ) { event ->
                when (event) {
                    ZoneInventoryEvent.AddZone -> {
                        scope.launch {
                            overlayHost.showInDialog(
                                screen = AddZoneScreen,
                                onGoToScreen = navigator::goTo
                            )
                        }
                    }

                    ZoneInventoryEvent.NextPage -> {
                        currentPage = (currentPage + 1).coerceAtMost(sectionPages - 1)
                    }

                    ZoneInventoryEvent.PreviousPage -> {
                        currentPage = (currentPage - 1).coerceAtLeast(0)
                    }
                }
            }
        }
    }
}

sealed interface ZoneInventoryUiState : CircuitUiState {

    data object Loading : ZoneInventoryUiState

    data class Zones(
        val zoneTable: ZoneTable,
        val zonesCount: Int,
        val currentPage: Int,
        val totalPage: Int,
        val eventSink: (ZoneInventoryEvent) -> Unit = {},
    ) : ZoneInventoryUiState
}

sealed interface ZoneInventoryEvent : CircuitUiEvent {
    data object AddZone : ZoneInventoryEvent
    data object PreviousPage : ZoneInventoryEvent
    data object NextPage : ZoneInventoryEvent
}
