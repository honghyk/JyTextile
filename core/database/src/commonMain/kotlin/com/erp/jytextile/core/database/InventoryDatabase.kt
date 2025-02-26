package com.erp.jytextile.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.erp.jytextile.core.database.dao.InventoryDao
import com.erp.jytextile.core.database.dao.InventoryZoneDao
import com.erp.jytextile.core.database.model.FabricRollEntity
import com.erp.jytextile.core.database.model.ReleaseHistoryEntity
import com.erp.jytextile.core.database.model.ZoneEntity
import com.erp.jytextile.core.database.util.InstantConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [ZoneEntity::class, FabricRollEntity::class, ReleaseHistoryEntity::class],
    version = 1
)
@TypeConverters(
    InstantConverter::class,
)
@ConstructedBy(InventoryDatabaseConstructor::class)
abstract class InventoryDatabase : RoomDatabase() {
    abstract fun inventoryDao(): InventoryDao
    abstract fun inventoryZoneDao(): InventoryZoneDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object InventoryDatabaseConstructor : RoomDatabaseConstructor<InventoryDatabase> {
    override fun initialize(): InventoryDatabase
}

internal fun getInventoryDatabase(
    builder: RoomDatabase.Builder<InventoryDatabase>,
): InventoryDatabase = builder
    .setDriver(BundledSQLiteDriver())
    .setQueryCoroutineContext(Dispatchers.IO)
    .build()
