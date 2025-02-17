package com.erp.jytextile.feature.inventory.roll

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.base.parcel.Parcelize
import com.erp.jytextile.core.designsystem.component.JyButton
import com.erp.jytextile.core.designsystem.component.JyOutlinedButton
import com.erp.jytextile.core.designsystem.component.PanelSurface
import com.erp.jytextile.core.designsystem.theme.JyTheme
import com.erp.jytextile.core.designsystem.theme.Palette
import com.erp.jytextile.core.domain.model.LengthUnit
import com.erp.jytextile.feature.inventory.common.ui.FormField
import com.erp.jytextile.feature.inventory.common.ui.FormHeader
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
                    label = "ZONE",
                    value = state.zoneName,
                    onValueChange = { state.eventSink(RollFormEvent.UpdateZoneName(it)) },
                )
                FormField(
                    label = "ITEM NO",
                    value = state.itemNo,
                    onValueChange = { state.eventSink(RollFormEvent.UpdateItemNo(it)) },
                )
                FormField(
                    label = "COLOR",
                    value = state.color,
                    onValueChange = { state.eventSink(RollFormEvent.UpdateColor(it)) },
                )
                FormField(
                    label = "FACTORY",
                    value = state.factory,
                    onValueChange = { state.eventSink(RollFormEvent.UpdateFactory(it)) },
                )
                Row(
                    modifier = Modifier.height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    FormField(
                        modifier = modifier.weight(3f),
                        label = "QUANTITY",
                        value = state.quantity,
                        onValueChange = { state.eventSink(RollFormEvent.UpdateQuantity(it)) },
                        keyboardType = KeyboardType.Number,
                    )
                    LengthUnitDropDownMenu(
                        modifier = Modifier.weight(2f),
                        selected = state.lengthUnit,
                        onItemSelected = { state.eventSink(RollFormEvent.UpdateLengthUnit(it)) },
                    )
                }
                FormField(
                    label = "REMARK",
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

@Composable
private fun LengthUnitDropDownMenu(
    selected: LengthUnit,
    onItemSelected: (LengthUnit) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            style = JyTheme.typography.textSmall,
            color = Palette.grey700,
            text = "단위",
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(
                    width = 1.dp,
                    color = JyTheme.color.border,
                    shape = RoundedCornerShape(8.dp),
                )
                .clickable { expanded = !expanded }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    style = JyTheme.typography.textMedium,
                    color = Palette.grey700,
                    text = when (selected) {
                        LengthUnit.METER -> "Meter"
                        LengthUnit.YARD -> "Yard"
                    }
                )
                Image(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                )
            }
            DropdownMenu(
                containerColor = JyTheme.color.surface,
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                DropdownMenuItem(
                    text = { Text(text = "METER") },
                    onClick = {
                        onItemSelected(LengthUnit.METER)
                        expanded = false
                    },
                )
                DropdownMenuItem(
                    text = { Text(text = "YARD") },
                    onClick = {
                        onItemSelected(LengthUnit.YARD)
                        expanded = false
                    },
                )
            }
        }
    }
}
