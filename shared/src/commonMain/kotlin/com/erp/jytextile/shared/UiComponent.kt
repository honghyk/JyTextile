package com.erp.jytextile.shared

import com.erp.jytextile.feature.form.release.ReleaseFormComponent
import com.erp.jytextile.feature.form.roll.RollFormComponent
import com.erp.jytextile.feature.form.zone.ZoneFormComponent
import com.erp.jytextile.feature.inventory.release.ReleaseHistoryComponent
import com.erp.jytextile.feature.inventory.roll.RollInventoryComponent
import com.erp.jytextile.feature.inventory.zone.ZoneInventoryComponent
import com.erp.jytextile.feature.rolldetail.RollDetailComponent
import com.erp.jytextile.feature.root.AppContent
import com.erp.jytextile.feature.root.RootComponent
import com.erp.jytextile.feature.search.SearchComponent
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
    RollDetailComponent,
    SearchComponent,
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
