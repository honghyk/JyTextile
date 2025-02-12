package com.erp.jytextile.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.theme.JyTheme

@Composable
fun JyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = JyButtonDefaults.shape,
    colors: ButtonColors = JyButtonDefaults.primaryButtonColors(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = JyButtonDefaults.ButtonContentPadding,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        onClick = onClick,
        content = content,
    )
}

@Composable
fun JyOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = JyButtonDefaults.shape,
    colors: ButtonColors = JyButtonDefaults.outlinedButtonColors(),
    border: BorderStroke? = JyButtonDefaults.outlinedButtonBorder(enabled),
    contentPadding: PaddingValues = JyButtonDefaults.ButtonContentPadding,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        onClick = onClick,
        content = content,
    )
}

object JyButtonDefaults {
    val ButtonContentPadding = PaddingValues(
        horizontal = 16.dp,
        vertical = 10.dp,
    )

    val shape = RoundedCornerShape(4.dp)

    @Composable
    fun primaryButtonColors(): ButtonColors = ButtonDefaults.buttonColors(
        containerColor = JyTheme.color.primary,
        contentColor = JyTheme.color.onPrimary,
    )

    @Composable
    fun outlinedButtonColors(): ButtonColors = ButtonDefaults.outlinedButtonColors(
        containerColor = JyTheme.color.surface,
        contentColor = JyTheme.color.subHeading,
    )

    @Composable
    fun outlinedButtonBorder(enabled: Boolean = true): BorderStroke =
        BorderStroke(
            width = 1.dp,
            color = JyTheme.color.border,
        )
}
