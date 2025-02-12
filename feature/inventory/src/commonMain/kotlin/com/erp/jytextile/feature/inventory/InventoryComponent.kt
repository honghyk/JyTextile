package com.erp.jytextile.feature.inventory

import com.erp.jytextile.feature.inventory.navigation.InventoryRouteFactory
import com.erp.jytextile.shared.base.inject.ActivityScope
import com.erp.jytextile.shared.base.route.AppRouteFactory
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface InventoryComponent {

    @Provides
    @ActivityScope
    @IntoSet
    fun bindInventoryRouteFactory(factory: InventoryRouteFactory): AppRouteFactory = factory
}
