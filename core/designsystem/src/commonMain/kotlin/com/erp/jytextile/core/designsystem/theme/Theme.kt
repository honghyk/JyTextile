package com.erp.jytextile.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalJyTypography = staticCompositionLocalOf<JyTypography> {
    error("No typography provided")
}

private val LocalJyColor = staticCompositionLocalOf<JyColor> {
    error("No color provided")
}

@Composable
fun JyTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalJyTypography provides jyTypography,
        LocalJyColor provides colorPalette
    ) {
        MaterialTheme(
            content = content,
        )
    }
}

object JyTheme {
    val typography: JyTypography
        @Composable
        get() = LocalJyTypography.current

    val color: JyColor
        @Composable
        get() = LocalJyColor.current
}
