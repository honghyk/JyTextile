package com.erp.jytextile.feature.root

import com.erp.jytextile.shared.base.inject.ActivityScope
import me.tatarka.inject.annotations.Provides

interface RootComponent {

    @Provides
    @ActivityScope
    fun bindJyContent(appContent: DefaultAppContent): AppContent = appContent
}
