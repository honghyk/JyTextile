package com.erp.trillion.core.data.di

import com.erp.trillion.core.data.repository.ReleaseHistoryRepositoryImpl
import com.erp.trillion.core.data.repository.RollInventoryRepositoryImpl
import com.erp.trillion.core.data.repository.SearchRepositoryImpl
import com.erp.trillion.core.data.repository.ZoneInventoryRepositoryImpl
import com.erp.trillion.core.domain.repository.ReleaseHistoryRepository
import com.erp.trillion.core.domain.repository.RollInventoryRepository
import com.erp.trillion.core.domain.repository.SearchRepository
import com.erp.trillion.core.domain.repository.ZoneInventoryRepository
import me.tatarka.inject.annotations.Provides

interface RepositoryComponent {

    @Provides
    fun bindZoneInventoryRepository(impl: ZoneInventoryRepositoryImpl): ZoneInventoryRepository = impl

    @Provides
    fun bindRollInventoryRepository(impl: RollInventoryRepositoryImpl): RollInventoryRepository = impl

    @Provides
    fun bindReleaseHistoryRepository(impl: ReleaseHistoryRepositoryImpl): ReleaseHistoryRepository = impl

    @Provides
    fun bindsSearchRepository(impl: SearchRepositoryImpl): SearchRepository = impl
}
