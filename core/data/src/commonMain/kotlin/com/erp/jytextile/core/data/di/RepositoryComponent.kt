package com.erp.jytextile.core.data.di

import com.erp.jytextile.core.data.repository.ReleaseHistoryRepositoryImpl
import com.erp.jytextile.core.data.repository.RollInventoryRepositoryImpl
import com.erp.jytextile.core.data.repository.ZoneInventoryRepositoryImpl
import com.erp.jytextile.core.domain.repository.ReleaseHistoryRepository
import com.erp.jytextile.core.domain.repository.RollInventoryRepository
import com.erp.jytextile.core.domain.repository.ZoneInventoryRepository
import me.tatarka.inject.annotations.Provides

interface RepositoryComponent {

    @Provides
    fun bindZoneInventoryRepository(impl: ZoneInventoryRepositoryImpl): ZoneInventoryRepository = impl

    @Provides
    fun bindRollInventoryRepository(impl: RollInventoryRepositoryImpl): RollInventoryRepository = impl

    @Provides
    fun bindReleaseHistoryRepository(impl: ReleaseHistoryRepositoryImpl): ReleaseHistoryRepository = impl
}
