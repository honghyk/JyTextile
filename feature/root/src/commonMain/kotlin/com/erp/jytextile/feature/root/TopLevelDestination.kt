package com.erp.jytextile.feature.root

import com.erp.jytextile.core.designsystem.icon.JyIcons
import com.erp.jytextile.feature.inventory.navigation.InventoryRoute
import org.jetbrains.compose.resources.DrawableResource
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val icon: DrawableResource,
    val iconText: String,
    val route: KClass<*>,
    val baseRoute: KClass<*>
) {
    Inventory(
        icon = JyIcons.Inventory,
        iconText = "Inventory",
        route = InventoryRoute::class,
        baseRoute = InventoryRoute::class,
    )
}
