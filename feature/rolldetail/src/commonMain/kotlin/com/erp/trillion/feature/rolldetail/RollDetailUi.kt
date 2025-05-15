package com.erp.trillion.feature.rolldetail

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.erp.trillion.core.designsystem.component.PanelSurface
import com.erp.trillion.core.designsystem.component.TrillionButton
import com.erp.trillion.core.designsystem.theme.Palette
import com.erp.trillion.core.designsystem.theme.TrillionTheme
import com.erp.trillion.core.navigation.RollDetailScreen
import com.erp.trillion.core.ui.model.FabricRollUiModel
import com.erp.trillion.kotlin.utils.formatDecimal
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
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 20.dp)
                ) {
                    Row(
                        modifier = Modifier.align(Alignment.End),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            style = TrillionTheme.typography.textLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = TrillionTheme.color.heading,
                            maxLines = 1,
                            text = "Roll #${state.roll.id}",
                        )
                        TrillionButton(onClick = { state.eventSink(RollDetailEvent.Release) }) {
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
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides TrillionTheme.typography.textMedium.copy(color = Palette.grey700)
        ) {
            Column(
                modifier = Modifier.width(IntrinsicSize.Max),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(text = "Zone")
                Text(text = "Order No")
                Text(text = "Item No")
            }
            Spacer(modifier = Modifier.width(32.dp))
            Column(
                modifier = Modifier.width(IntrinsicSize.Max),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(text = roll.zone.name)
                Text(text = roll.orderNo)
                Text(text = roll.itemNo)
            }
            VerticalDivider(color = TrillionTheme.color.divider)
            Column(
                modifier = Modifier.width(IntrinsicSize.Max),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(text = "Factory")
                Text(text = "Qty(M)")
                Text(text = "Qty(Y)")
            }
            Spacer(modifier = Modifier.width(32.dp))
            Column(
                modifier = Modifier.width(IntrinsicSize.Max),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(text = roll.factory)
                Text(
                    text = buildString {
                        append(roll.remainingQuantity.formatDecimal(decimalPlaces = 1))
                        append("/")
                        append(roll.originalQuantity.formatDecimal(decimalPlaces = 1))
                    }
                )
                Text(
                    text = buildString {
                        append(roll.remainingQuantityInYard.formatDecimal(decimalPlaces = 1))
                        append("/")
                        append(roll.originalQuantityInYard.formatDecimal(decimalPlaces = 1))
                    }
                )
            }
            VerticalDivider(color = TrillionTheme.color.divider)
            Column(
                modifier = Modifier.width(IntrinsicSize.Max),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(text = "Finish")
                Text(text = "Remark")
            }
            Spacer(modifier = Modifier.width(32.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(text = roll.finish)
                Text(text = roll.remark.orEmpty())
            }
        }
    }
}
