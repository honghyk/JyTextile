package com.erp.jytextile.feature.root

import com.erp.jytextile.core.designsystem.icon.JyIcons
import com.erp.jytextile.feature.inventory.SectionInventoryScreen
import com.slack.circuit.runtime.screen.Screen
import org.jetbrains.compose.resources.DrawableResource

enum class TopLevelDestination(
    val icon: DrawableResource,
    val iconText: String,
    val screen: Screen,
) {
    Inventory(
        icon = JyIcons.Inventory,
        iconText = "Inventory",
        screen = SectionInventoryScreen,
    )
}
