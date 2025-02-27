package com.erp.jytextile.core.database

import com.erp.jytextile.core.base.inject.Singleton
import com.erp.jytextile.core.database.dao.InventoryDao
import com.erp.jytextile.core.database.dao.InventoryZoneDao
import com.erp.jytextile.core.database.dao.ReleaseHistoryDao
import com.erp.jytextile.core.database.dao.RollInventoryDao
import me.tatarka.inject.annotations.Provides

expect interface InventoryDatabasePlatformComponent

interface InventoryDatabaseComponent : InventoryDatabasePlatformComponent {

    @Singleton
    @Provides
    fun provideInventoryDao(database: InventoryDatabase): InventoryDao = database.inventoryDao()

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
