package com.erp.jytextile.shared.database

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.erp.jytextile.shared.base.inject.Singleton
import me.tatarka.inject.annotations.Provides

actual interface InventoryDatabasePlatformComponent {

    @Singleton
    @Provides
    fun provideInventoryDatabase(application: Application): InventoryDatabase {
        return getInventoryDatabase(getInventoryDatabaseBuilder(application))
    }

    private fun getInventoryDatabaseBuilder(application: Application): RoomDatabase.Builder<InventoryDatabase> {
        val context = application.applicationContext
        val dbFile = context.getDatabasePath("inventory.db")
        return Room.databaseBuilder(
            context,
            InventoryDatabase::class.java, dbFile.path
        )
    }
}
