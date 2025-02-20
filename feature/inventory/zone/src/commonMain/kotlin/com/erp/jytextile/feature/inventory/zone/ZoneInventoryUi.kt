package com.erp.jytextile.feature.inventory.zone

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.erp.jytextile.core.designsystem.component.JyButton
import com.erp.jytextile.core.designsystem.component.JyOutlinedButton
import com.erp.jytextile.core.designsystem.theme.Dimension
import com.erp.jytextile.core.navigation.ZoneInventoryScreen
import com.erp.jytextile.core.ui.TablePanel
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
                ZoneInventory(
                    table = state.zoneTable,
                    currentPage = state.currentPage,
                    totalPage = state.totalPage,
                    onZoneClick = { state.eventSink(ZoneInventoryEvent.ToRolls(it)) },
                    onAddRollClick = { state.eventSink(ZoneInventoryEvent.AddRoll) },
                    onAddZoneClick = { state.eventSink(ZoneInventoryEvent.AddZone) },
                    onPreviousClick = { state.eventSink(ZoneInventoryEvent.PreviousPage) },
                    onNextClick = { state.eventSink(ZoneInventoryEvent.NextPage) },
                )
            }
        }
    }
}

@Composable
private fun ZoneInventory(
    table: ZoneTable,
    currentPage: Int,
    totalPage: Int,
    onZoneClick: (Long) -> Unit,
    onAddRollClick: () -> Unit,
    onAddZoneClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        TablePanel(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            title = "Zones",
            table = table,
            currentPage = currentPage,
            totalPage = totalPage,
            onPreviousClick = onPreviousClick,
            onNextClick = onNextClick,
            onItemClick = { onZoneClick(it.id) },
        ) {
            JyOutlinedButton(onClick = onAddZoneClick) {
                Text(
                    maxLines = 1,
                    text = "Zone 추가"
                )
            }
            JyButton(onClick = onAddRollClick) {
                Text(
                    maxLines = 1,
                    text = "Roll 입고"
                )
            }
        }
    }
}
