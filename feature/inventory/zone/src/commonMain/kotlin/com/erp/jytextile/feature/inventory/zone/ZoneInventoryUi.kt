package com.erp.jytextile.feature.inventory.zone

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.erp.jytextile.core.designsystem.component.ColumnWidth
import com.erp.jytextile.core.designsystem.component.JyButton
import com.erp.jytextile.core.designsystem.component.LoadingContent
import com.erp.jytextile.core.designsystem.component.PaginatedFixedTable
import com.erp.jytextile.core.designsystem.component.PanelSurface
import com.erp.jytextile.core.designsystem.icon.JyIcons
import com.erp.jytextile.core.designsystem.theme.Dimension
import com.erp.jytextile.core.navigation.ZoneInventoryScreen
import com.erp.jytextile.core.ui.model.ZoneTable
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject
import org.jetbrains.compose.resources.vectorResource

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
            ZoneInventoryUiState.Loading -> {
                PanelSurface {
                    LoadingContent(modifier = Modifier.fillMaxSize())
                }
            }

            is ZoneInventoryUiState.Zones -> {
                ZoneInventoryPanel(
                    modifier = Modifier.fillMaxSize(),
                    table = state.zoneTable,
                    onZoneClick = { state.eventSink(ZoneInventoryEvent.ToRolls(it)) },
                    onAddRollClick = { state.eventSink(ZoneInventoryEvent.AddRoll) },
                    onAddZoneClick = { state.eventSink(ZoneInventoryEvent.AddZone) },
                    onRemoveZoneClick = { state.eventSink(ZoneInventoryEvent.RemoveZone(it)) },
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
    onZoneClick: (Long) -> Unit,
    onAddRollClick: () -> Unit,
    onAddZoneClick: () -> Unit,
    onRemoveZoneClick: (Long) -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    PaginatedFixedTable(
        modifier = modifier,
        title = "Zones",
        columnWidths = listOf(
            ColumnWidth.Weight(1f),
            ColumnWidth.MaxIntrinsicWidth,
            ColumnWidth.MaxIntrinsicWidth
        ),
        rows = table.items,
        onNext = onNextClick,
        onPrevious = onPreviousClick,
        onRowClick = { onZoneClick(it.id) },
        headerActions = {
            JyButton(
                onClick = onAddZoneClick,
                content = { Text("Zone 추가") }
            )
        },
        headerRowContent = { column ->
            when (column) {
                2 -> HeaderCell(modifier = Modifier, header = "")
                else -> HeaderCell(modifier = Modifier, header = table.headers[column])
            }
        },
        rowContent = { item, column ->
            when (column) {
                0 -> PrimaryTextCell(
                    modifier = Modifier,
                    text = item.name
                )

                1 -> TextCell(
                    modifier = Modifier,
                    text = item.tableRow[column]
                )

                2 -> IconButtonCell(
                    modifier = Modifier,
                    icon = vectorResource(JyIcons.Delete),
                    onClick = { onRemoveZoneClick(item.id) }
                )
            }
        }
    )
}
