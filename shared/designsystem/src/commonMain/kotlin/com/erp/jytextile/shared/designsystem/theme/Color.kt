package com.erp.jytextile.shared.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class JyColor(
    val primary: Color,
    val body: Color,
    val heading: Color,
    val subHeading: Color,
    val totalReturned: Color,
    val totalProducts: Color,
    val inStock: Color,
    val outOfStock: Color,
    val border: Color,
)

val colorPalette = JyColor(
    primary = Color(0xFF1570EF),
    body = Color(0xFF858D9D),
    heading = Color(0xFF383E49),
    subHeading = Color(0xFF5D6679),
    totalReturned = Color(0xFF845EBC),
    totalProducts = Color(0xFFDBA362),
    inStock = Color(0xFF12B76A),
    outOfStock = Color(0xFFF04438),
    border = Color(0xFFF0F1F3),
)
