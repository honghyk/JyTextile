package com.erp.jytextile.feature.inventory.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.erp.jytextile.feature.inventory.InventoryScreen
import com.erp.jytextile.feature.inventory.InventoryViewModel
import com.erp.jytextile.core.base.route.AppRouteFactory
import kotlinx.serialization.Serializable
import me.tatarka.inject.annotations.Inject

@Serializable
data object InventoryRoute

fun NavController.navigateToInventory(navOptions: NavOptions? = null) =
    navigate(InventoryRoute, navOptions)

@Inject
class InventoryRouteFactory(
    private val viewModel: () -> InventoryViewModel,
) : AppRouteFactory {
    override fun NavGraphBuilder.create(
        navController: NavController
    ) {
        composable<InventoryRoute> {
            InventoryScreen(
                viewModel = viewModel(),
            )
        }
    }
}
