package com.erp.trillion.core.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.erp.trillion.core.base.inject.Singleton
import me.tatarka.inject.annotations.Provides
import java.io.File

actual interface InventoryDatabasePlatformComponent {

    @Singleton
    @Provides
    fun provideInventoryDatabase(): InventoryDatabase {
        return getInventoryDatabase(getInventoryDatabaseBuilder())
    }

    private fun getInventoryDatabaseBuilder(): RoomDatabase.Builder<InventoryDatabase> {
        val dbFile = File(getDatabasePath(), "inventory.db")
        return Room.databaseBuilder<InventoryDatabase>(
            name = dbFile.absolutePath,
        )
    }

    private fun getDatabasePath(): String {
        val baseDir = when {
            System.getProperty("os.name").contains("Windows", ignoreCase = true) ->
                System.getenv("LOCALAPPDATA")
                    ?: (System.getProperty("user.home") + "\\AppData\\Local")

            System.getProperty("os.name").contains("Mac", ignoreCase = true) ->
                System.getProperty("user.home") + "/Library/Application Support"

            else -> // Linux or other Unix-based systems
                System.getProperty("user.home") + "/.local/share"
        }

        val appDir = File(baseDir, "Trillion")
        if (!appDir.exists()) {
            appDir.mkdirs()
        }
        return appDir.absolutePath
    }
}
