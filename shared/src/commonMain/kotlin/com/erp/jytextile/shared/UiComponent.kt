package com.erp.jytextile.shared

import com.erp.jytextile.feature.inventory.section.SectionFormComponent
import com.erp.jytextile.feature.inventory.section.SectionInventoryComponent
import com.erp.jytextile.feature.root.AppContent
import com.erp.jytextile.feature.root.RootComponent
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.Provides

interface UiComponent
    : SectionInventoryComponent,
    SectionFormComponent,
    RootComponent {

    val appContent: AppContent

    @Provides
    fun provideCircuit(
        uiFactories: Set<Ui.Factory>,
        presenterFactories: Set<Presenter.Factory>,
    ): Circuit = Circuit.Builder()
        .addUiFactories(uiFactories)
        .addPresenterFactories(presenterFactories)
        .build()
}
