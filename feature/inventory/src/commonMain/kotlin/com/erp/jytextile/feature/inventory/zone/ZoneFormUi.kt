package com.erp.jytextile.feature.inventory.zone

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
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.base.parcel.Parcelize
import com.erp.jytextile.core.designsystem.component.JyButton
import com.erp.jytextile.core.designsystem.component.JyOutlinedButton
import com.erp.jytextile.core.designsystem.component.PanelSurface
import com.erp.jytextile.feature.inventory.common.ui.FormField
import com.erp.jytextile.feature.inventory.common.ui.FormHeader
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.screen.StaticScreen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject

@Parcelize
data object AddZoneScreen : StaticScreen

@Inject
class ZoneFormUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
        is AddZoneScreen -> {
            ui<ZoneFormUiState> { state, modifier -> AddZoneUi(state, modifier) }
        }

        else -> null
    }
}

@Composable
fun AddZoneUi(
    state: ZoneFormUiState,
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
            FormHeader(text = "신규 Zone 추가")
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                FormField(
                    label = "Zone 이름",
                    value = state.name,
                    hint = "Ex) A-1",
                    onValueChange = { state.eventSink(ZoneFormEvent.UpdateName(it)) },
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.align(Alignment.End),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                JyOutlinedButton(onClick = { state.eventSink(ZoneFormEvent.Discard) }) {
                    Text(
                        maxLines = 1,
                        text = "취소"
                    )
                }
                JyButton(
                    enabled = state.submittable,
                    onClick = { state.eventSink(ZoneFormEvent.Submit) }
                ) {
                    Text(
                        maxLines = 1,
                        text = "Zone 추가"
                    )
                }
            }
        }
    }
}
