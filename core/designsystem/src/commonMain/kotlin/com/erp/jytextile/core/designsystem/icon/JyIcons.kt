package com.erp.jytextile.core.designsystem.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.DrawableResource
import textileerp.core.designsystem.generated.resources.Res
import textileerp.core.designsystem.generated.resources.basket_alt_3_light
import textileerp.core.designsystem.generated.resources.home_light
import textileerp.core.designsystem.generated.resources.ic_arrow_left
import textileerp.core.designsystem.generated.resources.ic_arrow_right
import textileerp.core.designsystem.generated.resources.ic_delete
import textileerp.core.designsystem.generated.resources.search_empty

object JyIcons {
    val Inventory: DrawableResource = Res.drawable.home_light
    val Release: DrawableResource = Res.drawable.basket_alt_3_light
    val ArrowBack: ImageVector = Icons.AutoMirrored.Rounded.ArrowBack
    val SearchEmpty: DrawableResource = Res.drawable.search_empty
    val ArrowLeft: DrawableResource = Res.drawable.ic_arrow_left
    val ArrowRight: DrawableResource = Res.drawable.ic_arrow_right
    val Delete: DrawableResource = Res.drawable.ic_delete
}
