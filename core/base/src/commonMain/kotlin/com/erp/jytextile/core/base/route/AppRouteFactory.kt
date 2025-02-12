package com.erp.jytextile.core.base.route

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface AppRouteFactory {

    fun NavGraphBuilder.create(
        navController: NavController,
    )
}

fun AppRouteFactory.create(
    navGraphBuilder: NavGraphBuilder,
    navController: NavController,
) {
    navGraphBuilder.create(navController)
}
