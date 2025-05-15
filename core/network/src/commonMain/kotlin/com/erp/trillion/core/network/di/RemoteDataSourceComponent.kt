package com.erp.trillion.core.network.di

import com.erp.trillion.core.base.inject.Singleton
import com.erp.trillion.core.data.datasource.remote.FabricRollRemoteDataSource
import com.erp.trillion.core.data.datasource.remote.ReleaseHistoryRemoteDataSource
import com.erp.trillion.core.data.datasource.remote.ZoneRemoteDataSource
import com.erp.trillion.core.network.BuildConfig
import com.erp.trillion.core.network.datasource.FabricRollRemoteDataSourceImpl
import com.erp.trillion.core.network.datasource.ReleaseHistoryRemoteDataSourceImpl
import com.erp.trillion.core.network.datasource.ZoneRemoteDataSourceImpl
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.logging.LogLevel
import io.github.jan.supabase.postgrest.Postgrest
import me.tatarka.inject.annotations.Provides

interface RemoteDataSourceComponent {

    @Singleton
    @Provides
    fun provideHttpClient(): SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://oueewwpgkatseruadsgw.supabase.co",
        supabaseKey = BuildConfig.SUPABASE_API_KEY
    ) {
        defaultLogLevel = LogLevel.DEBUG
        install(Postgrest)
    }

    @Singleton
    @Provides
    fun bindsZoneRemoteDataSource(impl: ZoneRemoteDataSourceImpl): ZoneRemoteDataSource = impl

    @Singleton
    @Provides
    fun bindsFabricRollRemoteDataSource(impl: FabricRollRemoteDataSourceImpl): FabricRollRemoteDataSource = impl

    @Singleton
    @Provides
    fun bindsReleaseHistoryRemoteDataSource(impl: ReleaseHistoryRemoteDataSourceImpl): ReleaseHistoryRemoteDataSource = impl
}
