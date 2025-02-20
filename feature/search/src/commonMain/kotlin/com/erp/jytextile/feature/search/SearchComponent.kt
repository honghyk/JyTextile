package com.erp.jytextile.feature.search

import com.erp.jytextile.core.base.inject.ActivityScope
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface SearchComponent {

    @Provides
    @ActivityScope
    @IntoSet
    fun bindUiFactory(factory: SearchUiFactory): Ui.Factory = factory

    @Provides
    @ActivityScope
    @IntoSet
    fun bindPresenterFactory(factory: SearchPresenterFactory): Presenter.Factory = factory
}
