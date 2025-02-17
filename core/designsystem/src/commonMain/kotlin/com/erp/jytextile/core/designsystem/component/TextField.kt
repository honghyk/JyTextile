package com.erp.jytextile.core.designsystem.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.theme.JyTheme
import com.erp.jytextile.core.designsystem.theme.Palette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textStyle: TextStyle = JyTheme.typography.textMedium,
    placeholder: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = RoundedCornerShape(8.dp),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    color: Color = Palette.grey700,
    placeholderColor: Color = Palette.grey400,
    containerColor: Color = Color.Transparent,
) {
    val colors = TextFieldDefaults.colors(
        focusedTextColor = color,
        focusedPlaceholderColor = placeholderColor,
        focusedContainerColor = containerColor,
        unfocusedTextColor = color,
        unfocusedPlaceholderColor = placeholderColor,
        unfocusedContainerColor = containerColor,
        focusedIndicatorColor = JyTheme.color.border,
        unfocusedIndicatorColor = JyTheme.color.border,
        disabledTextColor = Palette.grey400,
        disabledPlaceholderColor = placeholderColor.copy(alpha = 0.38f),
        disabledContainerColor = JyTheme.color.border.copy(alpha = 0.38f),
        disabledIndicatorColor = JyTheme.color.border,
    )
    val mergedTextStyle = textStyle.merge(TextStyle(color = color))

    BasicTextField(
        value = value,
        modifier = modifier,
        onValueChange = onValueChange,
        enabled = enabled,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(color),
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
                    placeholder = placeholder,
                    singleLine = singleLine,
                    enabled = enabled,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
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
                        )
                    }
                )
            }
    )
}
