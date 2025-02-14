package com.erp.jytextile.feature.inventory.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.erp.jytextile.core.base.parcel.Parcelize
import com.erp.jytextile.core.base.route.AppRouteFactory
//import com.erp.jytextile.feature.inventory.SectionInventoryScreen
//import com.erp.jytextile.feature.inventory.SectionInventoryViewModel
import com.slack.circuit.runtime.screen.StaticScreen
import kotlinx.serialization.Serializable
import me.tatarka.inject.annotations.Inject

@Serializable
data object InventoryRoute

@Parcelize
data object InventoryScreen : StaticScreen

//fun NavController.navigateToInventory(navOptions: NavOptions? = null) =
//    navigate(InventoryRoute, navOptions)
//
//@Inject
//class InventoryRouteFactory(
//    private val viewModel: () -> SectionInventoryViewModel,
//) : AppRouteFactory {
//    override fun NavGraphBuilder.create(
//        navController: NavController
//    ) {
//        composable<InventoryRoute> {
//            SectionInventoryScreen(
//                viewModel = viewModel(),
//            )
//        }
//    }
//}
