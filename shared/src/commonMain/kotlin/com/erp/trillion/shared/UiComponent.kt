package com.erp.trillion.shared

import com.erp.trillion.feature.form.release.ReleaseFormComponent
import com.erp.trillion.feature.form.roll.RollFormComponent
import com.erp.trillion.feature.form.zone.ZoneFormComponent
import com.erp.trillion.feature.inventory.release.ReleaseHistoryComponent
import com.erp.trillion.feature.inventory.roll.RollInventoryComponent
import com.erp.trillion.feature.inventory.zone.ZoneInventoryComponent
import com.erp.trillion.feature.rolldetail.RollDetailComponent
import com.erp.trillion.feature.root.AppContent
import com.erp.trillion.feature.root.RootComponent
import com.erp.trillion.feature.search.SearchComponent
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
