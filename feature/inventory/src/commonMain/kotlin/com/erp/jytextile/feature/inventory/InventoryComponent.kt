package com.erp.jytextile.feature.inventory

import com.erp.jytextile.core.base.inject.ActivityScope
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface InventoryComponent {

//    @Provides
//    @ActivityScope
//    @IntoSet
//    fun bindInventoryRouteFactory(factory: InventoryRouteFactory): AppRouteFactory = factory

    @Provides
    @ActivityScope
    @IntoSet
    fun bindUiFactory(factory: SectionInventoryUiFactory): Ui.Factory = factory

    @Provides
    @ActivityScope
    @IntoSet
    fun bindPresenterFactory(factory: SectionInventoryPresenterFactory): Presenter.Factory = factory
}
