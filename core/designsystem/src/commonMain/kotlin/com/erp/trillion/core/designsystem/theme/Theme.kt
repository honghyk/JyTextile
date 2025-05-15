package com.erp.trillion.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalTrillionTypography = staticCompositionLocalOf<TrillionTypography> {
    error("No typography provided")
}

private val LocalTrillionColor = staticCompositionLocalOf<TrillionColor> {
    error("No color provided")
}

@Composable
fun TrillionTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalTrillionTypography provides trillionTypography,
        LocalTrillionColor provides colorPalette
    ) {
        MaterialTheme(
            content = content,
        )
    }
}

object TrillionTheme {
    val typography: TrillionTypography
        @Composable
        get() = LocalTrillionTypography.current

    val color: TrillionColor
        @Composable
        get() = LocalTrillionColor.current
}
