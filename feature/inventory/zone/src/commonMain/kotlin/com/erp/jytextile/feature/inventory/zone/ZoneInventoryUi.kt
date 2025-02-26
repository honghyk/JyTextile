package com.erp.jytextile.feature.inventory.zone

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.erp.jytextile.core.designsystem.component.JyButton
import com.erp.jytextile.core.designsystem.component.JyOutlinedButton
import com.erp.jytextile.core.designsystem.theme.Dimension
import com.erp.jytextile.core.navigation.ZoneInventoryScreen
import com.erp.jytextile.core.ui.TablePanel
import com.erp.jytextile.core.ui.model.TableItem
import com.erp.jytextile.core.ui.model.ZoneTable
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject

@Inject
class ZoneInventoryUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
        is ZoneInventoryScreen -> {
            ui<ZoneInventoryUiState> { state, modifier ->
                ZoneInventoryUi(state, modifier)
            }
        }

        else -> null
    }
}


@Composable
fun ZoneInventoryUi(
    state: ZoneInventoryUiState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.padding(Dimension.backgroundPadding),
    ) {
        when (state) {
            ZoneInventoryUiState.Loading -> { /* TODO */
            }

            is ZoneInventoryUiState.Zones -> {
                ZoneInventoryPanel(
                    modifier = Modifier.fillMaxSize(),
                    table = state.zoneTable,
                    selectedRows = state.selectedRows,
                    onZoneClick = { state.eventSink(ZoneInventoryEvent.ToRolls(it)) },
                    onZoneSelected = { state.eventSink(ZoneInventoryEvent.SelectRow(it)) },
                    onAddRollClick = { state.eventSink(ZoneInventoryEvent.AddRoll) },
                    onAddZoneClick = { state.eventSink(ZoneInventoryEvent.AddZone) },
                    onRemoveZoneClick = { state.eventSink(ZoneInventoryEvent.RemoveZone) },
                    onPreviousClick = { state.eventSink(ZoneInventoryEvent.PreviousPage) },
                    onNextClick = { state.eventSink(ZoneInventoryEvent.NextPage) },
                )
            }
        }
    }
}

@Composable
private fun ZoneInventoryPanel(
    table: ZoneTable,
    selectedRows: List<TableItem>,
    onZoneClick: (Long) -> Unit,
    onZoneSelected: (TableItem) -> Unit,
    onAddRollClick: () -> Unit,
    onAddZoneClick: () -> Unit,
    onRemoveZoneClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TablePanel(
        modifier = modifier,
        title = "Zones",
        table = table,
        selectedRows = selectedRows,
        onItemSelected = { _, item -> onZoneSelected(item) },
        onPreviousClick = onPreviousClick,
        onNextClick = onNextClick,
        onItemClick = { onZoneClick(it.id) },
    ) {
        JyOutlinedButton(
            onClick = onAddZoneClick,
            content = { Text(maxLines = 1, text = "Zone 추가") }
        )
        JyOutlinedButton(
            onClick = onRemoveZoneClick,
            enabled = selectedRows.isNotEmpty(),
            content = { Text(maxLines = 1, text = "Zone 삭제") }
        )
        JyButton(
            onClick = onAddRollClick,
            content = { Text(maxLines = 1, text = "Roll 입고") }
        )
    }
}
