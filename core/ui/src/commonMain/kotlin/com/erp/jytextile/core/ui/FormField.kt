package com.erp.jytextile.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.component.JyButton
import com.erp.jytextile.core.designsystem.component.JyDropDownMenu
import com.erp.jytextile.core.designsystem.component.JyOutlinedButton
import com.erp.jytextile.core.designsystem.component.OutlinedTextField
import com.erp.jytextile.core.designsystem.component.PanelSurface
import com.erp.jytextile.core.designsystem.theme.JyTheme
import com.erp.jytextile.core.domain.model.LengthUnit

@Composable
fun FormPanel(
    title: String,
    primaryButtonEnabled: Boolean,
    onCancelButtonClick: () -> Unit,
    onPrimaryButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    primaryButtonContent: @Composable RowScope.() -> Unit,
    cancelButtonContent: @Composable (RowScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    PanelSurface(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 28.dp,
                    vertical = 24.dp
                ),
        ) {
            FormPanelTitle(text = title)
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                content = content,
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.align(Alignment.End),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                JyOutlinedButton(
                    onClick = onCancelButtonClick
                ) {
                    if (cancelButtonContent == null) {
                        Text(maxLines = 1, text = "취소")
                    } else {
                        cancelButtonContent()
                    }
                }
                JyButton(
                    enabled = primaryButtonEnabled,
                    onClick = onPrimaryButtonClick,
                    content = primaryButtonContent,
                )
            }
        }
    }
}

@Composable
fun FormTextField(
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
        FormLabel(label = label)
        OutlinedTextField(
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
fun <T> DropDownFormField(
    label: String,
    items: List<T>,
    selected: T?,
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    textGetter: (T) -> String = { it.toString() },
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        FormLabel(label = label)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .border(
                    width = 1.dp,
                    color = JyTheme.color.border,
                    shape = RoundedCornerShape(8.dp),
                )
                .clip(RoundedCornerShape(8.dp))
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
                    text = selected?.let(textGetter).orEmpty(),
                )
                Image(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                )
            }
            JyDropDownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = textGetter(item)) },
                        onClick = {
                            onItemSelected(item)
                            expanded = false
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun RollQuantityFormField(
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
        FormTextField(
            modifier = modifier.weight(3f),
            label = "Quantity",
            value = quantity,
            onValueChange = onQuantityChange,
            keyboardType = KeyboardType.Number,
        )
        DropDownFormField(
            modifier = Modifier.weight(2f),
            label = "Length Unit",
            items = LengthUnit.entries,
            selected = lengthUnit,
            onItemSelected = onLengthUnitChange,
            textGetter = {
                when (it) {
                    LengthUnit.METER -> "Meter"
                    LengthUnit.YARD -> "Yard"
                }
            }
        )
    }
}

@Composable
private fun FormPanelTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        style = JyTheme.typography.textXLarge,
        color = JyTheme.color.heading,
        text = text,
    )
}

@Composable
private fun FormLabel(
    label: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        style = JyTheme.typography.textSmall,
        text = label
    )
}
