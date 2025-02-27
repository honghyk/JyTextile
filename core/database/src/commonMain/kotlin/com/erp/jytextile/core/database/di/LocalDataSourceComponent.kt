package com.erp.jytextile.core.database.di

import com.erp.jytextile.core.base.inject.Singleton
import com.erp.jytextile.core.database.datasource.ZoneLocalDataSource
import com.erp.jytextile.core.database.datasource.ZoneLocalDataSourceImpl
import me.tatarka.inject.annotations.Provides

interface LocalDataSourceComponent {

    @Provides
    @Singleton
    fun providesZoneLocalDataSource(impl: ZoneLocalDataSourceImpl): ZoneLocalDataSource = impl
}
