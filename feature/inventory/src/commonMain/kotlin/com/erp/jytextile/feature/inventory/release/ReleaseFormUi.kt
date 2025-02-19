package com.erp.jytextile.feature.inventory.release

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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
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
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject

@Parcelize
data class ReleaseFormScreen(
    val rollId: Long,
    val rollItemNo: String
) : Screen

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
fun ReleaseFormUi(
    state: ReleaseFormUiState,
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
            FormHeader(text = "ROLL 출고")
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                FormField(
                    label = "Roll Id",
                    value = state.rollId,
                    enabled = false,
                    onValueChange = {},
                )
                FormField(
                    label = "Roll Item No",
                    value = state.rollItemNo,
                    enabled = false,
                    onValueChange = {},
                )
                FormField(
                    label = "Buyer",
                    value = state.buyer,
                    onValueChange = { state.eventSink(ReleaseFormEvent.UpdateBuyer(it)) },
                )
                QuantityFormField(
                    quantity = state.quantity,
                    lengthUnit = state.lengthUnit,
                    onQuantityChange = { state.eventSink(ReleaseFormEvent.UpdateQuantity(it)) },
                    onLengthUnitChange = { state.eventSink(ReleaseFormEvent.UpdateLengthUnit(it)) },
                )
                FormField(
                    label = "Out date",
                    value = state.releaseDate,
                    hint = "2025-01-01",
                    visualTransformation = DateVisualTransformation,
                    onValueChange = { state.eventSink(ReleaseFormEvent.UpdateReleaseDate(it)) },
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.align(Alignment.End),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                JyOutlinedButton(onClick = { state.eventSink(ReleaseFormEvent.Discard) }) {
                    Text(
                        maxLines = 1,
                        text = "취소"
                    )
                }
                JyButton(
                    enabled = state.canSubmit,
                    onClick = { state.eventSink(ReleaseFormEvent.Submit) }
                ) {
                    Text(
                        maxLines = 1,
                        text = "ROLL 출고"
                    )
                }
            }
        }
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
