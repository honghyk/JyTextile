package com.erp.jytextile.feature.inventory.common.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.component.FormTextField
import com.erp.jytextile.core.designsystem.theme.JyTheme
import com.erp.jytextile.core.designsystem.theme.Palette

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
