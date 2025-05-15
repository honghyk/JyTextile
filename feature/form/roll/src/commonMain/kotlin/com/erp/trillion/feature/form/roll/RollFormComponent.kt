package com.erp.trillion.feature.form.roll

import com.erp.trillion.core.base.inject.ActivityScope
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface RollFormComponent {

    @Provides
    @ActivityScope
    @IntoSet
    fun bindUiFactory(factory: RollFormUiFactory): Ui.Factory = factory

    @Provides
    @ActivityScope
    @IntoSet
    fun bindPresenterFactory(factory: RollFormPresenterFactory): Presenter.Factory = factory
}
