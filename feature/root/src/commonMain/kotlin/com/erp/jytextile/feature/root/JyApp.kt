package com.erp.jytextile.feature.root

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.erp.jytextile.feature.inventory.navigation.InventoryRoute
import com.erp.jytextile.shared.base.route.AppRouteFactory
import com.erp.jytextile.shared.base.route.create
import com.erp.jytextile.shared.designsystem.theme.JyTheme

@Composable
fun JyApp(
    routeFactories: Set<AppRouteFactory>,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier,
        containerColor = JyTheme.color.surfaceDim,
    ) { padding ->
        JyNavHost(
            modifier = Modifier.padding(padding),
            routeFactories = routeFactories,
            navController = navController
        )
    }
}

@Composable
fun JyNavHost(
    routeFactories: Set<AppRouteFactory>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = InventoryRoute,
    ) {
        routeFactories.forEach { factory -> factory.create(this, navController) }
    }
}
