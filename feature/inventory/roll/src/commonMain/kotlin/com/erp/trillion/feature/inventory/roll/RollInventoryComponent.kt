package com.erp.trillion.feature.inventory.roll

import com.erp.trillion.core.base.inject.ActivityScope
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface RollInventoryComponent {

    @Provides
    @ActivityScope
    @IntoSet
    fun bindUiFactory(factory: RollInventoryUiFactory): Ui.Factory = factory

    @Provides
    @ActivityScope
    @IntoSet
    fun bindPresenterFactory(factory: RollInventoryPresenterFactory): Presenter.Factory = factory
}
