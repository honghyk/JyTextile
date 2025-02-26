package com.erp.jytextile.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.erp.jytextile.core.database.InventoryDatabase
import com.erp.jytextile.core.database.model.ZoneEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class InventoryZoneDaoTest {

    private lateinit var zoneDao: InventoryZoneDao
    private lateinit var db: InventoryDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, InventoryDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        zoneDao = db.inventoryZoneDao()
    }

    @After
    fun tearDown() = db.close()

    @Test
    fun test_delete_page() = runTest {
        val zones = List(30) { testZoneEntity(it) }
        zoneDao.insert(zones)

        zoneDao.deletePage(0, 10)

        val result = zoneDao.getZones(0, 30).first()
        assertEquals(
            zones.drop(10),
            result,
        )
    }

    @Test
    fun test_delete_page_in_the_middle_of_the_list() = runTest {
        val zones = List(50) { testZoneEntity(it) }
        zoneDao.insert(zones)

        zoneDao.deletePage(10, 10)

        val result = zoneDao.getZones(0, 50).first()
        assertEquals(
            zones.subList(0, 10) + zones.subList(20, 50),
            result
        )
    }
}

private fun testZoneEntity(id: Int) = ZoneEntity(
    id = id.toLong(),
    name = "name$id",
    rollCount = 0,
)
