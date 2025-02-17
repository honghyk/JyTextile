package com.erp.jytextile.feature.inventory.common.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.component.FormTextField
import com.erp.jytextile.core.designsystem.theme.JyTheme
import com.erp.jytextile.core.designsystem.theme.Palette
import com.erp.jytextile.core.domain.model.LengthUnit

@Composable
fun FormHeader(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        style = JyTheme.typography.textXLarge,
        color = JyTheme.color.heading,
        text = text,
    )
}

@Composable
fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    singleLine: Boolean = true,
    enabled: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        FormLabel(
            style = JyTheme.typography.textSmall,
            label = label,
        )
        FormTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            enabled = enabled,
            onValueChange = onValueChange,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            placeholder = { Text(text = hint) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        )
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

@Composable
fun QuantityFormField(
    quantity: String,
    lengthUnit: LengthUnit,
    onQuantityChange: (String) -> Unit,
    onLengthUnitChange: (LengthUnit) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        FormField(
            modifier = modifier.weight(3f),
            label = "Quantity",
            value = quantity,
            onValueChange = onQuantityChange,
            keyboardType = KeyboardType.Number,
        )
        LengthUnitDropDownMenu(
            modifier = Modifier.weight(2f),
            selected = lengthUnit,
            onItemSelected = onLengthUnitChange,
        )
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
