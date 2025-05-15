package com.erp.trillion.feature.root

import com.erp.trillion.core.designsystem.icon.TrillionIcons
import com.erp.trillion.core.navigation.SearchScreen
import com.erp.trillion.core.navigation.ZoneInventoryScreen
import com.slack.circuit.runtime.screen.Screen
import org.jetbrains.compose.resources.DrawableResource

enum class TopLevelDestination(
    val icon: DrawableResource,
    val iconText: String,
    val screen: Screen,
) {
    Inventory(
        icon = TrillionIcons.Inventory,
        iconText = "Inventory",
        screen = ZoneInventoryScreen,
    ),
    Release(
        icon = TrillionIcons.Release,
        iconText = "출고",
        screen = SearchScreen,
    ),
}
