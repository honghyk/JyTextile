package com.erp.jytextile.feature.inventory.zone

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.component.JyButton
import com.erp.jytextile.core.designsystem.component.JyOutlinedButton
import com.erp.jytextile.core.ui.TablePanel
import com.erp.jytextile.core.ui.model.ZoneTable
import com.erp.jytextile.feature.inventory.common.ui.InventoryOverallPanel
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
        modifier = modifier
            .padding(
                horizontal = 32.dp,
                vertical = 22.dp,
            ),
    ) {
        when (state) {
            ZoneInventoryUiState.Loading -> { /* TODO */
            }

            is ZoneInventoryUiState.Zones -> {
                ZoneInventory(
                    table = state.zoneTable,
                    zonesCount = state.zonesCount,
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
    zonesCount: Int,
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
        InventoryOverallPanel(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            total = zonesCount,
            title = "Zones",
        )
        Spacer(modifier = Modifier.height(22.dp))
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
