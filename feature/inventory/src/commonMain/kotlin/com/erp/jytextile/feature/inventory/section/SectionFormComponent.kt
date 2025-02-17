package com.erp.jytextile.feature.inventory.section

import com.erp.jytextile.core.base.inject.ActivityScope
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface SectionFormComponent {

    @Provides
    @ActivityScope
    @IntoSet
    fun bindUiFactory(factory: SectionFormUiFactory): Ui.Factory = factory

    @Provides
    @ActivityScope
    @IntoSet
    fun bindPresenterFactory(factory: SectionFormPresenterFactory): Presenter.Factory = factory
}
