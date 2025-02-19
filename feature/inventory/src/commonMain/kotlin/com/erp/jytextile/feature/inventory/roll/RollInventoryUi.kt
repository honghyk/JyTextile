package com.erp.jytextile.feature.inventory.roll

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.component.JyButton
import com.erp.jytextile.core.designsystem.component.JyOutlinedButton
import com.erp.jytextile.core.designsystem.component.JyTopAppBar
import com.erp.jytextile.core.designsystem.component.PanelSurface
import com.erp.jytextile.core.designsystem.theme.JyTheme
import com.erp.jytextile.core.designsystem.theme.Palette
import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.feature.inventory.common.ui.InventoryTablePanel
import com.erp.jytextile.feature.inventory.roll.model.RollTable
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject
import kotlin.math.round

@Inject
class RollInventoryUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
        is RollInventoryScreen -> {
            ui<RollInventoryUiState> { state, modifier ->
                RollInventoryUi(state, modifier)
            }
        }

        else -> null
    }
}


@Composable
fun RollInventoryUi(
    state: RollInventoryUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        JyTopAppBar(
            onBackClick = { state.eventSink(RollInventoryEvent.Back) },
            title = {}
        )
        Box(
            modifier = modifier
                .weight(1f)
                .padding(
                    horizontal = 32.dp,
                    vertical = 22.dp,
                ),
        ) {
            when (state) {
                is RollInventoryUiState.Loading -> { /* TODO */
                }

                is RollInventoryUiState.Rolls -> {
                    RollInventory(
                        table = state.rollTable,
                        currentPage = state.currentPage,
                        totalPage = state.totalPage,
                        selectedRoll = state.selectedRoll,
                        onItemClick = { state.eventSink(RollInventoryEvent.RollSelected(it)) },
                        onPreviousClick = { state.eventSink(RollInventoryEvent.PreviousPage) },
                        onNextClick = { state.eventSink(RollInventoryEvent.NextPage) },
                        onRemoveClick = { state.eventSink(RollInventoryEvent.Remove) },
                        onReleaseClick = { state.eventSink(RollInventoryEvent.Release) },
                        onReleaseHistoryClick = { state.eventSink(RollInventoryEvent.ReleaseHistory) }
                    )
                }
            }
        }
    }
}

@Composable
private fun RollInventory(
    table: RollTable,
    currentPage: Int,
    totalPage: Int,
    selectedRoll: FabricRoll?,
    onItemClick: (Long) -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onRemoveClick: () -> Unit,
    onReleaseClick: () -> Unit,
    onReleaseHistoryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        RollDetailPanel(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            roll = selectedRoll,
            onRemoveClick = onRemoveClick,
            onReleaseClick = onReleaseClick,
            onReleaseHistoryClick = onReleaseHistoryClick
        )
        Spacer(modifier = Modifier.height(22.dp))
        InventoryTablePanel(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            title = "Rolls",
            table = table,
            currentPage = currentPage,
            totalPage = totalPage,
            onItemClick = { onItemClick(it.id) },
            onPreviousClick = onPreviousClick,
            onNextClick = onNextClick,
            headerButtonContent = { },
        )
    }
}

@Composable
private fun RollDetailPanel(
    roll: FabricRoll?,
    onRemoveClick: () -> Unit,
    onReleaseClick: () -> Unit,
    onReleaseHistoryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    PanelSurface(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 180.dp),
    ) {
        if (roll != null) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                Row {
                    Text(
                        style = JyTheme.typography.textXLarge,
                        color = JyTheme.color.heading,
                        maxLines = 1,
                        text = roll.itemNo,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        JyButton(onClick = onReleaseClick) {
                            Text(
                                maxLines = 1,
                                text = "Roll 출고"
                            )
                        }
                        JyOutlinedButton(onClick = onReleaseHistoryClick) {
                            Text(
                                maxLines = 1,
                                text = "출고 이력"
                            )
                        }
                        JyOutlinedButton(onClick = onRemoveClick) {
                            Text(
                                maxLines = 1,
                                text = "삭제"
                            )
                        }
                    }
                }
                CompositionLocalProvider(
                    LocalTextStyle provides JyTheme.typography.textMedium.copy(color = Palette.grey700)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(80.dp),
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(text = "No: ${roll.id}")
                            Text(text = "Zone: ${roll.zone.name}")
                            Text(text = "Color: ${roll.color}")
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(text = "Factory: ${roll.factory}")
                            Text(text = "Qty(M): ${(round(roll.remainingLength * 10) / 10)}/${roll.originalLength}")
                            Text(text = "Remark: ${roll.remark}")
                        }
                    }
                }
            }
        }
    }
}
