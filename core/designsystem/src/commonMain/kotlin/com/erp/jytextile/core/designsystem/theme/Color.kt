package com.erp.jytextile.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

object Palette {
    val grey400 = Color(0xFF858D9D)
    val grey500 = Color(0xFF667085)
    val grey600 = Color(0xFF5D6679)
    val grey700 = Color(0xFF48505E)
    val grey800 = Color(0xFF383E49)
}

@Immutable
data class JyColor(
    val primary: Color,
    val onPrimary: Color,
    val body: Color,
    val heading: Color,
    val subHeading: Color,
    val totalReturned: Color,
    val totalProducts: Color,
    val inStock: Color,
    val outOfStock: Color,
    val border: Color,
    val surface: Color,
    val surfaceDim: Color,
)

val colorPalette = JyColor(
    primary = Color(0xFF1570EF),
    onPrimary = Color(0xFFFFFFFF),
    body = Palette.grey400,
    heading = Palette.grey800,
    subHeading = Palette.grey600,
    totalReturned = Color(0xFF845EBC),
    totalProducts = Color(0xFFDBA362),
    inStock = Color(0xFF12B76A),
    outOfStock = Color(0xFFF04438),
    border = Color(0xFFF0F1F3),
    surface = Color(0xFFFFFFFF),
    surfaceDim = Color(0xFFF0F1F3),
)
