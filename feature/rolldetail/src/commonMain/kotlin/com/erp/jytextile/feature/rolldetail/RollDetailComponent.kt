package com.erp.jytextile.feature.rolldetail

import com.erp.jytextile.core.base.inject.ActivityScope
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface RollDetailComponent {

    @Provides
    @ActivityScope
    @IntoSet
    fun bindUiFactory(factory: RollDetailUiFactory): Ui.Factory = factory

    @Provides
    @ActivityScope
    @IntoSet
    fun bindPresenterFactory(factory: RollDetailPresenterFactory): Presenter.Factory = factory
}
