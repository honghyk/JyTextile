package com.erp.jytextile.feature.inventory.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.base.parcel.Parcelize
import com.erp.jytextile.core.designsystem.component.FormTextField
import com.erp.jytextile.core.designsystem.component.JyButton
import com.erp.jytextile.core.designsystem.component.JyOutlinedButton
import com.erp.jytextile.core.designsystem.component.PanelSurface
import com.erp.jytextile.core.designsystem.theme.JyTheme
import com.erp.jytextile.core.designsystem.theme.Palette
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.screen.StaticScreen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject

@Parcelize
data object AddSectionScreen : StaticScreen

@Inject
class SectionFormUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
        is AddSectionScreen -> {
            ui<SectionFormUiState> { state, modifier -> AddSectionUi(state, modifier) }
        }

        else -> null
    }
}

@Composable
fun AddSectionUi(
    state: SectionFormUiState,
    modifier: Modifier = Modifier
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
            Text(
                style = JyTheme.typography.textXLarge,
                color = JyTheme.color.heading,
                text = "New Section"
            )
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                FormField(
                    label = "Section Name",
                    value = state.name,
                    hint = "Enter section name",
                    onValueChange = { state.eventSink(SectionFormEvent.UpdateName(it)) },
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.align(Alignment.End),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                JyOutlinedButton(onClick = { state.eventSink(SectionFormEvent.Discard) }) {
                    Text(
                        maxLines = 1,
                        text = "Discard"
                    )
                }
                JyButton(
                    enabled = state.submittable,
                    onClick = { state.eventSink(SectionFormEvent.Submit) }
                ) {
                    Text(
                        maxLines = 1,
                        text = "Add Section"
                    )
                }
            }
        }
    }
}

@Composable
private fun FormField(
    label: String,
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    BoxWithConstraints {
        val isPhone = maxWidth <= 480.dp
        if (isPhone) {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                FormLabel(
                    style = JyTheme.typography.textSmall,
                    label = label,
                )
                FormTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = value,
                    onValueChange = onValueChange,
                    placeholder = { Text(text = hint) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
                )
            }
        } else {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                FormLabel(
                    modifier = Modifier.width(200.dp),
                    style = JyTheme.typography.textMedium,
                    label = label
                )
                FormTextField(
                    modifier = Modifier.weight(1f),
                    value = value,
                    onValueChange = onValueChange,
                    placeholder = { Text(text = hint) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
                )
            }
        }
    }
}

@Composable
private fun FormLabel(
    label: String,
    style: TextStyle,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        style = style,
        color = Palette.grey700,
        text = label
    )
}
