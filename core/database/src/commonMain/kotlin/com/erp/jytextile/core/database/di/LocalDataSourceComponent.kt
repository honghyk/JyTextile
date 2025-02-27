package com.erp.jytextile.core.database.di

import com.erp.jytextile.core.base.inject.Singleton
import com.erp.jytextile.core.database.datasource.FabricRollLocalDataSource
import com.erp.jytextile.core.database.datasource.FabricRollLocalDataSourceImpl
import com.erp.jytextile.core.database.datasource.ReleaseHistoryLocalDataSource
import com.erp.jytextile.core.database.datasource.ReleaseHistoryLocalDataSourceImpl
import com.erp.jytextile.core.database.datasource.ZoneLocalDataSource
import com.erp.jytextile.core.database.datasource.ZoneLocalDataSourceImpl
import me.tatarka.inject.annotations.Provides

interface LocalDataSourceComponent {

    @Provides
    @Singleton
    fun providesZoneLocalDataSource(impl: ZoneLocalDataSourceImpl): ZoneLocalDataSource = impl

    @Provides
    @Singleton
    fun providesRollLocalDataSource(impl: FabricRollLocalDataSourceImpl): FabricRollLocalDataSource = impl

    @Provides
    @Singleton
    fun providesReleaseHistoryLocalDataSource(impl: ReleaseHistoryLocalDataSourceImpl): ReleaseHistoryLocalDataSource = impl
}
