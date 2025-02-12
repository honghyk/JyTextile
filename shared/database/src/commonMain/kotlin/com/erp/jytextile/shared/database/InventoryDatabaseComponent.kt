package com.erp.jytextile.shared.database

import com.erp.jytextile.shared.base.inject.Singleton
import com.erp.jytextile.shared.database.dao.InventoryDao
import me.tatarka.inject.annotations.Provides

expect interface InventoryDatabasePlatformComponent

interface InventoryDatabaseComponent : InventoryDatabasePlatformComponent {

    @Singleton
    @Provides
    fun provideInventoryDao(database: InventoryDatabase): InventoryDao = database.inventoryDao()
}
