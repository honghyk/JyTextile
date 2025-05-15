package com.erp.trillion.feature.form.zone

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erp.trillion.core.navigation.ZoneFormScreen
import com.erp.trillion.core.ui.FormPanel
import com.erp.trillion.core.ui.FormTextField
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject

@Inject
class ZoneFormUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
        is ZoneFormScreen -> {
            ui<ZoneFormUiState> { state, modifier -> AddZoneUi(state, modifier) }
        }

        else -> null
    }
}

@Composable
private fun AddZoneUi(
    state: ZoneFormUiState,
    modifier: Modifier = Modifier
) {
    FormPanel(
        modifier = modifier.padding(top = 80.dp, bottom = 32.dp),
        title = "Zone 추가",
        primaryButtonEnabled = state.submittable,
        onCancelButtonClick = { state.eventSink(ZoneFormEvent.Discard) },
        onPrimaryButtonClick = { state.eventSink(ZoneFormEvent.Submit) },
        primaryButtonContent = { Text(text = "추가") },
    ) {
        FormTextField(
            label = "이름",
            value = state.name,
            hint = "Ex) A-1",
            onValueChange = { state.eventSink(ZoneFormEvent.UpdateName(it)) },
        )
    }
}
