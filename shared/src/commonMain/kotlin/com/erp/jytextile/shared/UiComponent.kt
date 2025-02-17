package com.erp.jytextile.shared

import com.erp.jytextile.feature.inventory.release.ReleaseFormComponent
import com.erp.jytextile.feature.inventory.release.ReleaseHistoryComponent
import com.erp.jytextile.feature.inventory.roll.RollFormComponent
import com.erp.jytextile.feature.inventory.roll.RollInventoryComponent
import com.erp.jytextile.feature.inventory.zone.ZoneFormComponent
import com.erp.jytextile.feature.inventory.zone.ZoneInventoryComponent
import com.erp.jytextile.feature.root.AppContent
import com.erp.jytextile.feature.root.RootComponent
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.Provides

interface UiComponent
    : ZoneInventoryComponent,
    ZoneFormComponent,
    RollInventoryComponent,
    RollFormComponent,
    ReleaseFormComponent,
    ReleaseHistoryComponent,
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
