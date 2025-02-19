package com.erp.jytextile.feature.form.zone

import com.erp.jytextile.core.base.inject.ActivityScope
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface ZoneFormComponent {

    @Provides
    @ActivityScope
    @IntoSet
    fun bindUiFactory(factory: ZoneFormUiFactory): Ui.Factory = factory

    @Provides
    @ActivityScope
    @IntoSet
    fun bindPresenterFactory(factory: ZoneFormPresenterFactory): Presenter.Factory = factory
}
