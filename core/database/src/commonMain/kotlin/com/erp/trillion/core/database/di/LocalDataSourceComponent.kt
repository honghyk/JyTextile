package com.erp.trillion.core.database.di

import com.erp.trillion.core.base.inject.Singleton
import com.erp.trillion.core.data.datasource.local.FabricRollLocalDataSource
import com.erp.trillion.core.data.datasource.local.ReleaseHistoryLocalDataSource
import com.erp.trillion.core.data.datasource.local.ZoneLocalDataSource
import com.erp.trillion.core.database.datasource.FabricRollLocalDataSourceImpl
import com.erp.trillion.core.database.datasource.ReleaseHistoryLocalDataSourceImpl
import com.erp.trillion.core.database.datasource.ZoneLocalDataSourceImpl
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
