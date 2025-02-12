package com.erp.jytextile.shared.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.erp.jytextile.shared.base.inject.Singleton
import me.tatarka.inject.annotations.Provides
import java.io.File

actual interface InventoryDatabasePlatformComponent {

    @Singleton
    @Provides
    fun provideInventoryDatabase(): InventoryDatabase {
        return getInventoryDatabase(getInventoryDatabaseBuilder())
    }

    private fun getInventoryDatabaseBuilder(): RoomDatabase.Builder<InventoryDatabase> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), "inventory.db")
        return Room.databaseBuilder<InventoryDatabase>(
            name = dbFile.absolutePath,
        )
    }
}
