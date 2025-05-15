package com.erp.trillion.core.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.erp.trillion.core.base.inject.Singleton
import kotlinx.cinterop.ExperimentalForeignApi
import me.tatarka.inject.annotations.Provides
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual interface InventoryDatabasePlatformComponent {

    @Singleton
    @Provides
    fun provideInventoryDatabase(): InventoryDatabase {
        return getInventoryDatabase(getInventoryDatabaseBuilder())
    }

    private fun getInventoryDatabaseBuilder(): RoomDatabase.Builder<InventoryDatabase> {
        val dbFilePath = documentDirectory() + "/inventory.db"
        return Room.databaseBuilder<InventoryDatabase>(
            name = dbFilePath,
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory?.path)
    }
}
