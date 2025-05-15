package com.erp.trillion.feature.inventory.zone

import com.erp.trillion.core.base.inject.ActivityScope
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface ZoneInventoryComponent {

    @Provides
    @ActivityScope
    @IntoSet
    fun bindUiFactory(factory: ZoneInventoryUiFactory): Ui.Factory = factory

    @Provides
    @ActivityScope
    @IntoSet
    fun bindPresenterFactory(factory: ZoneInventoryPresenterFactory): Presenter.Factory = factory
}
