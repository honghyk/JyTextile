package com.erp.jytextile.feature.inventory.roll

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.base.parcel.Parcelize
import com.erp.jytextile.core.designsystem.component.JyButton
import com.erp.jytextile.core.designsystem.component.JyOutlinedButton
import com.erp.jytextile.core.designsystem.component.PanelSurface
import com.erp.jytextile.feature.inventory.common.ui.FormField
import com.erp.jytextile.feature.inventory.common.ui.FormHeader
import com.erp.jytextile.feature.inventory.common.ui.QuantityFormField
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.screen.StaticScreen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject

@Parcelize
data object RollFormScreen : StaticScreen

@Inject
class RollFormUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
        is RollFormScreen -> {
            ui<RollFormUiState> { state, modifier -> RollFormUi(state, modifier) }
        }

        else -> null
    }
}

@Composable
fun RollFormUi(
    state: RollFormUiState,
    modifier: Modifier = Modifier,
) {
    PanelSurface(
        modifier = modifier.padding(top = 80.dp, bottom = 32.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 28.dp,
                    vertical = 24.dp
                ),
        ) {
            FormHeader(text = "신규 ROLL 추가")
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                FormField(
                    label = "Zone",
                    value = state.zoneName,
                    onValueChange = { state.eventSink(RollFormEvent.UpdateZoneName(it)) },
                )
                FormField(
                    label = "No",
                    value = state.id,
                    keyboardType = KeyboardType.Number,
                    onValueChange = { state.eventSink(RollFormEvent.UpdateId(it)) },
                )
                FormField(
                    label = "Item No",
                    value = state.itemNo,
                    onValueChange = { state.eventSink(RollFormEvent.UpdateItemNo(it)) },
                )
                FormField(
                    label = "Order No",
                    value = state.orderNo,
                    onValueChange = { state.eventSink(RollFormEvent.UpdateOrderNo(it)) },
                )
                FormField(
                    label = "Color",
                    value = state.color,
                    onValueChange = { state.eventSink(RollFormEvent.UpdateColor(it)) },
                )
                FormField(
                    label = "Factory",
                    value = state.factory,
                    onValueChange = { state.eventSink(RollFormEvent.UpdateFactory(it)) },
                )
                QuantityFormField(
                    quantity = state.quantity,
                    lengthUnit = state.lengthUnit,
                    onQuantityChange = { state.eventSink(RollFormEvent.UpdateQuantity(it)) },
                    onLengthUnitChange = { state.eventSink(RollFormEvent.UpdateLengthUnit(it)) },
                )
                FormField(
                    label = "Remark",
                    value = state.remark,
                    singleLine = false,
                    onValueChange = { state.eventSink(RollFormEvent.UpdateRemark(it)) },
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.align(Alignment.End),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                JyOutlinedButton(onClick = { state.eventSink(RollFormEvent.Discard) }) {
                    Text(
                        maxLines = 1,
                        text = "취소"
                    )
                }
                JyButton(
                    enabled = state.canSubmit,
                    onClick = { state.eventSink(RollFormEvent.Submit) }
                ) {
                    Text(
                        maxLines = 1,
                        text = "ROLL 추가"
                    )
                }
            }
        }
    }
}
