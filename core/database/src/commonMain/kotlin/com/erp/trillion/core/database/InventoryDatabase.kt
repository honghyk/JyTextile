package com.erp.trillion.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.erp.trillion.core.database.dao.InventoryZoneDao
import com.erp.trillion.core.database.dao.ReleaseHistoryDao
import com.erp.trillion.core.database.dao.RollInventoryDao
import com.erp.trillion.core.database.model.FabricRollEntity
import com.erp.trillion.core.database.model.ReleaseHistoryEntity
import com.erp.trillion.core.database.model.ZoneEntity
import com.erp.trillion.core.database.util.InstantConverter
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
    abstract fun inventoryZoneDao(): InventoryZoneDao
    abstract fun rollInventoryDao(): RollInventoryDao
    abstract fun releaseHistoryDao(): ReleaseHistoryDao
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
