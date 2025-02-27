package com.erp.jytextile.core.network.di

import com.erp.jytextile.core.base.inject.Singleton
import com.erp.jytextile.core.data.datasource.remote.ZoneRemoteDataSource
import com.erp.jytextile.core.network.BuildConfig
import com.erp.jytextile.core.network.datasource.ZoneRemoteDataSourceImpl
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
}
