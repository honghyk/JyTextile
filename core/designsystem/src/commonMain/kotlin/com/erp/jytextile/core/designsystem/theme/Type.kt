package com.erp.jytextile.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Immutable
data class JyTypography(
    val displaySmall: TextStyle,
    val displayXSmall: TextStyle,
    val textXLarge: TextStyle,
    val textLarge: TextStyle,
    val textMedium: TextStyle,
    val textSmall: TextStyle,
)

val jyTypography = JyTypography(
    displaySmall = TextStyle(
        fontSize = 30.sp,
        lineHeight = 38.sp,
    ),
    displayXSmall = TextStyle(
        fontSize = 24.sp,
        lineHeight = 32.sp,
    ),
    textXLarge = TextStyle(
        fontSize = 20.sp,
        lineHeight = 30.sp,
    ),
    textLarge = TextStyle(
        fontSize = 18.sp,
        lineHeight = 28.sp,
    ),
    textMedium = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    textSmall = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
    )
)
