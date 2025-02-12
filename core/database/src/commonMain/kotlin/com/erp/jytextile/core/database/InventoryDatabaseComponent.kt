package com.erp.jytextile.core.database

import com.erp.jytextile.core.base.inject.Singleton
import com.erp.jytextile.core.database.dao.InventoryDao
import me.tatarka.inject.annotations.Provides

expect interface InventoryDatabasePlatformComponent

interface InventoryDatabaseComponent : InventoryDatabasePlatformComponent {

    @Singleton
    @Provides
    fun provideInventoryDao(database: InventoryDatabase): InventoryDao = database.inventoryDao()
}
