package com.erp.jytextile.feature.rolldetail

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.component.JyButton
import com.erp.jytextile.core.designsystem.component.PanelSurface
import com.erp.jytextile.core.designsystem.theme.JyTheme
import com.erp.jytextile.core.designsystem.theme.Palette
import com.erp.jytextile.core.navigation.RollDetailScreen
import com.erp.jytextile.core.ui.model.FabricRollUiModel
import com.erp.jytextile.kotlin.utils.formatDecimal
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject

@Inject
class RollDetailUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
        is RollDetailScreen -> {
            ui<RollDetailUiState> { state, modifier -> RollDetailUi(state, modifier) }
        }

        else -> null
    }
}

@Composable
private fun RollDetailUi(
    state: RollDetailUiState,
    modifier: Modifier = Modifier,
) {
    PanelSurface(
        modifier = modifier.fillMaxWidth(),
    ) {
        when (state) {
            is RollDetailUiState.Loading -> { /* TODO */
            }

            is RollDetailUiState.RollDetail -> {
                Column(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 12.dp,
                        bottom = 20.dp
                    )
                ) {
                    Row(
                        modifier = Modifier.align(Alignment.End),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            style = JyTheme.typography.textXLarge,
                            color = JyTheme.color.heading,
                            maxLines = 1,
                            text = "Roll",
                        )
                        JyButton(onClick = { state.eventSink(RollDetailEvent.Release) }) {
                            Text(maxLines = 1, text = "Roll 출고")
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    RollDetailGrid(
                        modifier = Modifier.fillMaxWidth(),
                        roll = state.roll,
                    )
                }
            }
        }
    }
}

@Composable
private fun RollDetailGrid(
    roll: FabricRollUiModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(80.dp),
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides JyTheme.typography.textMedium.copy(color = Palette.grey700)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "No: ${roll.id}")
                Text(text = "Zone: ${roll.zone.name}")
                Text(text = "Order No: ${roll.orderNo}")
                Text(text = "Item No: ${roll.itemNo}")
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Factory: ${roll.factory}")
                Text(
                    text = buildString {
                        append("Qty(M): ")
                        append(roll.remainingQuantity.formatDecimal(decimalPlaces = 1))
                        append("/")
                        append(roll.originalQuantity.formatDecimal(decimalPlaces = 1))
                    }
                )
                Text(
                    text = buildString {
                        append("Qty(Y): ")
                        append(roll.remainingQuantityInYard.formatDecimal(decimalPlaces = 1))
                        append("/")
                        append(roll.originalQuantityInYard.formatDecimal(decimalPlaces = 1))
                    }
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Finish: ${roll.finish}")
                Text(text = "Remark: ${roll.remark}")
            }
        }
    }
}
