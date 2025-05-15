package com.erp.trillion.core.database

import com.erp.trillion.core.base.inject.Singleton
import com.erp.trillion.core.database.dao.InventoryZoneDao
import com.erp.trillion.core.database.dao.ReleaseHistoryDao
import com.erp.trillion.core.database.dao.RollInventoryDao
import me.tatarka.inject.annotations.Provides

expect interface InventoryDatabasePlatformComponent

interface InventoryDatabaseComponent : InventoryDatabasePlatformComponent {

    @Singleton
    @Provides
    fun provideInventoryZoneDao(database: InventoryDatabase): InventoryZoneDao =
        database.inventoryZoneDao()

    @Singleton
    @Provides
    fun provideRollInventoryDao(database: InventoryDatabase): RollInventoryDao =
        database.rollInventoryDao()

    @Singleton
    @Provides
    fun provideReleaseHistoryDao(database: InventoryDatabase): ReleaseHistoryDao =
        database.releaseHistoryDao()
}
