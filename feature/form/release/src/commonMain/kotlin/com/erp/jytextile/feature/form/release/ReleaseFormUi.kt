package com.erp.jytextile.feature.form.release

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.navigation.ReleaseFormScreen
import com.erp.jytextile.core.ui.FormPanel
import com.erp.jytextile.core.ui.FormTextField
import com.erp.jytextile.core.ui.RollQuantityFormField
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject

@Inject
class ReleaseFormUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
        is ReleaseFormScreen -> {
            ui<ReleaseFormUiState> { state, modifier -> ReleaseFormUi(state, modifier) }
        }

        else -> null
    }
}

@Composable
private fun ReleaseFormUi(
    state: ReleaseFormUiState,
    modifier: Modifier = Modifier,
) {
    FormPanel(
        modifier = modifier.padding(top = 80.dp, bottom = 32.dp),
        title = "ROLL 출고",
        primaryButtonEnabled = state.canSubmit,
        onCancelButtonClick = { state.eventSink(ReleaseFormEvent.Discard) },
        onPrimaryButtonClick = { state.eventSink(ReleaseFormEvent.Submit) },
        primaryButtonContent = {
            Text(maxLines = 1, text = "출고")
        }
    ) {
        FormTextField(
            label = "Roll Id",
            value = state.rollId,
            enabled = false,
            onValueChange = {},
        )
        FormTextField(
            label = "Roll Item No",
            value = state.rollItemNo,
            enabled = false,
            onValueChange = {},
        )
        FormTextField(
            label = "Buyer",
            value = state.buyer,
            onValueChange = { state.eventSink(ReleaseFormEvent.UpdateBuyer(it)) },
        )
        RollQuantityFormField(
            quantity = state.quantity,
            lengthUnit = state.lengthUnit,
            onQuantityChange = { state.eventSink(ReleaseFormEvent.UpdateQuantity(it)) },
            onLengthUnitChange = { state.eventSink(ReleaseFormEvent.UpdateLengthUnit(it)) },
        )
        FormTextField(
            label = "Out date",
            value = state.releaseDate,
            hint = "2025-01-01",
            visualTransformation = DateVisualTransformation,
            onValueChange = { state.eventSink(ReleaseFormEvent.UpdateReleaseDate(it)) },
        )
        FormTextField(
            label = "Remark",
            value = state.remark,
            onValueChange = { state.eventSink(ReleaseFormEvent.UpdateRemark(it)) },
        )
    }
}

private val DateVisualTransformation = VisualTransformation { text ->
    var out = ""
    for (i in text.indices) {
        out += text[i]
        if (i == 3 || i == 5) out += "-"
    }
    TransformedText(AnnotatedString(out), object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 3) return offset
            if (offset <= 5) return offset + 1
            return offset + 2
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 3) return offset
            if (offset <= 6) return offset - 1
            return offset - 2
        }
    })
}
