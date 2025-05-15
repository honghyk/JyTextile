package com.erp.trillion.feature.inventory.release

import com.erp.trillion.core.base.inject.ActivityScope
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface ReleaseHistoryComponent {

    @Provides
    @ActivityScope
    @IntoSet
    fun bindUiFactory(factory: ReleaseHistoryUiFactory): Ui.Factory = factory

    @Provides
    @ActivityScope
    @IntoSet
    fun bindPresenterFactory(factory: ReleaseHistoryPresenterFactory): Presenter.Factory = factory
}
