package com.erp.jytextile.feature.form.release

import com.erp.jytextile.core.base.inject.ActivityScope
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface ReleaseFormComponent {

    @Provides
    @ActivityScope
    @IntoSet
    fun bindUiFactory(factory: ReleaseFormUiFactory): Ui.Factory = factory

    @Provides
    @ActivityScope
    @IntoSet
    fun bindPresenterFactory(factory: ReleaseFormPresenterFactory): Presenter.Factory = factory
}
