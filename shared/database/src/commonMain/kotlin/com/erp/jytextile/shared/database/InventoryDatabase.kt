package com.erp.jytextile.shared.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.erp.jytextile.shared.database.dao.InventoryDao
import com.erp.jytextile.shared.database.model.FabricRollEntity
import com.erp.jytextile.shared.database.model.ReleaseHistoryEntity
import com.erp.jytextile.shared.database.model.SectionEntity
import com.erp.jytextile.shared.database.util.InstantConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [SectionEntity::class, FabricRollEntity::class, ReleaseHistoryEntity::class],
    version = 1,
)
@TypeConverters(
    InstantConverter::class,
)
@ConstructedBy(InventoryDatabaseConstructor::class)
abstract class InventoryDatabase : RoomDatabase() {
    abstract fun inventoryDao(): InventoryDao
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
