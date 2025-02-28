package com.erp.jytextile.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

object Palette {
    val grey50 = Color(0xFFFAFAFA)
    val grey200 = Color(0xFFE9EAEB)
    val grey300 = Color(0xFFD5D7DA)
    val grey400 = Color(0xFF858D9D)
    val grey500 = Color(0xFF717680)
    val grey600 = Color(0xFF535862)
    val grey700 = Color(0xFF48505E)
    val grey800 = Color(0xFF383E49)
    val grey900 = Color(0xFF181D27)
}

@Immutable
data class JyColor(
    val primary: Color,
    val onPrimary: Color,
    val body: Color,
    val heading: Color,
    val subHeading: Color,
    val tableHeading: Color,
    val tableHeaderRowBackground: Color,
    val tablePrimaryColumn: Color,
    val totalReturned: Color,
    val totalProducts: Color,
    val inStock: Color,
    val outOfStock: Color,
    val border: Color,
    val divider: Color,
    val surface: Color,
    val surfaceDim: Color,
)

val colorPalette = JyColor(
    primary = Color(0xFF1570EF),
    onPrimary = Color(0xFFFFFFFF),
    body = Palette.grey600,
    heading = Palette.grey900,
    subHeading = Palette.grey600,
    tableHeading = Palette.grey500,
    tableHeaderRowBackground = Palette.grey50,
    tablePrimaryColumn = Palette.grey900,
    totalReturned = Color(0xFF845EBC),
    totalProducts = Color(0xFFDBA362),
    inStock = Color(0xFF12B76A),
    outOfStock = Color(0xFFF04438),
    border = Palette.grey300,
    divider = Palette.grey200,
    surface = Color(0xFFFFFFFF),
    surfaceDim = Color(0xFFF0F1F3),
)
