package com.erp.trillion.feature.root

import com.erp.trillion.core.base.inject.ActivityScope
import me.tatarka.inject.annotations.Provides

interface RootComponent {

    @Provides
    @ActivityScope
    fun bindAppContent(appContent: DefaultAppContent): AppContent = appContent
}
