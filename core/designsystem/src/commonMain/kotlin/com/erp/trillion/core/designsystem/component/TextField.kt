package com.erp.trillion.core.designsystem.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.erp.trillion.core.designsystem.theme.Palette
import com.erp.trillion.core.designsystem.theme.TrillionTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TrillionTheme.typography.textMedium,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = RoundedCornerShape(8.dp),
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = TrillionTheme.color.body,
        focusedPlaceholderColor = Palette.grey400,
        focusedContainerColor = Color.Transparent,
        unfocusedTextColor = TrillionTheme.color.body,
        unfocusedPlaceholderColor = Palette.grey400,
        unfocusedContainerColor = Color.Transparent,
        focusedIndicatorColor = TrillionTheme.color.border,
        unfocusedIndicatorColor = TrillionTheme.color.border,
        disabledTextColor = Palette.grey400,
        disabledPlaceholderColor = Palette.grey400.copy(alpha = 0.38f),
        disabledContainerColor = TrillionTheme.color.border.copy(alpha = 0.38f),
        disabledIndicatorColor = TrillionTheme.color.border,
        cursorColor = TrillionTheme.color.body,
    )
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()
    val textColor = colors.textColor(enabled, focused)
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    BasicTextField(
        value = value,
        modifier = modifier,
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(colors.cursorColor),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        decorationBox =
            @Composable { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    value = value,
                    innerTextField = innerTextField,
                    enabled = enabled,
                    singleLine = singleLine,
                    visualTransformation = visualTransformation,
                    interactionSource = interactionSource,
                    isError = false,
                    label = label,
                    placeholder = placeholder,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    prefix = prefix,
                    suffix = suffix,
                    supportingText = supportingText,
                    colors = colors,
                    contentPadding = PaddingValues(
                        horizontal = 14.dp,
                        vertical = 10.dp,
                    ),
                    container = {
                        OutlinedTextFieldDefaults.Container(
                            enabled = enabled,
                            isError = false,
                            interactionSource = interactionSource,
                            colors = colors,
                            shape = shape,
                            focusedBorderThickness = 1.dp,
                            unfocusedBorderThickness = 1.dp,
                        )
                    }
                )
            }
    )
}

private fun TextFieldColors.textColor(
    enabled: Boolean,
    focused: Boolean
): Color {
    return when {
        !enabled -> disabledTextColor
        !focused -> unfocusedTextColor
        else -> focusedTextColor
    }
}
