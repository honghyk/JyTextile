package com.erp.trillion.core.designsystem.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.DrawableResource
import trillion.core.designsystem.generated.resources.Res
import trillion.core.designsystem.generated.resources.basket_alt_3_light
import trillion.core.designsystem.generated.resources.home_light
import trillion.core.designsystem.generated.resources.ic_arrow_left
import trillion.core.designsystem.generated.resources.ic_arrow_right
import trillion.core.designsystem.generated.resources.ic_delete
import trillion.core.designsystem.generated.resources.ic_edit
import trillion.core.designsystem.generated.resources.search_empty

object TrillionIcons {
    val Inventory: DrawableResource = Res.drawable.home_light
    val Release: DrawableResource = Res.drawable.basket_alt_3_light

    val Search: ImageVector = Icons.Default.Search
    val SearchEmpty: DrawableResource = Res.drawable.search_empty

    val ArrowBack: ImageVector = Icons.AutoMirrored.Rounded.ArrowBack
    val ArrowLeft: DrawableResource = Res.drawable.ic_arrow_left
    val ArrowRight: DrawableResource = Res.drawable.ic_arrow_right
    val ArrowDropDown: ImageVector = Icons.Default.ArrowDropDown

    val Delete: DrawableResource = Res.drawable.ic_delete
    val Edit: DrawableResource = Res.drawable.ic_edit
}
