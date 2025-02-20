package com.erp.jytextile.feature.form.roll

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.navigation.RollFormScreen
import com.erp.jytextile.core.ui.DropDownFormField
import com.erp.jytextile.core.ui.FormPanel
import com.erp.jytextile.core.ui.FormTextField
import com.erp.jytextile.core.ui.RollQuantityFormField
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject

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
private fun RollFormUi(
    state: RollFormUiState,
    modifier: Modifier = Modifier,
) {
    FormPanel(
        modifier = modifier.padding(top = 80.dp, bottom = 32.dp),
        title = "신규 ROLL 추가",
        primaryButtonEnabled = state.canSubmit,
        onCancelButtonClick = { state.eventSink(RollFormEvent.Discard) },
        onPrimaryButtonClick = { state.eventSink(RollFormEvent.Submit) },
        primaryButtonContent = {
            Text(maxLines = 1, text = "ROLL 추가")
        }
    ) {
        DropDownFormField(
            label = "Zone",
            items = state.zones,
            selected = state.selectedZone,
            onItemSelected = { state.eventSink(RollFormEvent.UpdateZone(it)) },
            textGetter = { it.name }
        )
        FormTextField(
            label = "No",
            value = state.id,
            keyboardType = KeyboardType.Number,
            onValueChange = { state.eventSink(RollFormEvent.UpdateId(it)) },
        )
        FormTextField(
            label = "Order No",
            value = state.orderNo,
            onValueChange = { state.eventSink(RollFormEvent.UpdateOrderNo(it)) },
        )
        FormTextField(
            label = "Item No",
            value = state.itemNo,
            onValueChange = { state.eventSink(RollFormEvent.UpdateItemNo(it)) },
        )
        FormTextField(
            label = "Color",
            value = state.color,
            onValueChange = { state.eventSink(RollFormEvent.UpdateColor(it)) },
        )
        FormTextField(
            label = "Factory",
            value = state.factory,
            onValueChange = { state.eventSink(RollFormEvent.UpdateFactory(it)) },
        )
        FormTextField(
            label = "Finish",
            value = state.finish,
            onValueChange = { state.eventSink(RollFormEvent.UpdateFinish(it)) },
        )
        RollQuantityFormField(
            quantity = state.quantity,
            lengthUnit = state.lengthUnit,
            onQuantityChange = { state.eventSink(RollFormEvent.UpdateQuantity(it)) },
            onLengthUnitChange = { state.eventSink(RollFormEvent.UpdateLengthUnit(it)) },
        )
        FormTextField(
            label = "Remark",
            value = state.remark,
            singleLine = false,
            onValueChange = { state.eventSink(RollFormEvent.UpdateRemark(it)) },
        )
    }
}
