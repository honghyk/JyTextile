package com.erp.jytextile.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.erp.jytextile.core.database.InventoryDatabase
import com.erp.jytextile.core.database.model.FabricRollEntity
import com.erp.jytextile.core.database.model.ReleaseHistoryEntity
import com.erp.jytextile.core.database.model.SectionEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class InventoryDaoTest {

    private lateinit var inventoryDao: InventoryDao
    private lateinit var db: InventoryDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, InventoryDatabase::class.java).build()
        inventoryDao = db.inventoryDao()
    }

    @After
    fun closeDb() = db.close()

    @Test
    fun get_sections_by_ascending_order() = runTest {
        val sections = listOf(
            testSection("a1"),
            testSection("c1"),
            testSection("a2"),
            testSection("d1"),
        )
        sections.forEach { section ->
            inventoryDao.insertSection(section)
        }

        val result = inventoryDao.getSections(10, 0).first()

        assertEquals(
            listOf("a1", "a2", "c1", "d1"),
            result.map { it.section.name },
        )
    }

    @Test
    fun get_sections_count() = runTest {
        val sections = listOf(
            testSection("a1"),
            testSection("a2"),
        )
        sections.forEach { section ->
            inventoryDao.insertSection(section)
        }

        val result = inventoryDao.getSectionsCount().first()

        assertEquals(2, result)
    }

    @Test
    fun get_sections_with_roll_count() = runTest {
        val section1 = inventoryDao.insertSection(testSection("a1"))
        val rolls = listOf(
            testFabricRoll(section1, "a1"),
            testFabricRoll(section1, "j3"),
            testFabricRoll(section1, "c1"),
        )
        rolls.forEach { roll ->
            inventoryDao.insertFabricRoll(roll)
        }

        val result = inventoryDao.getSections(10, 0).first()

        assertEquals(3, result.first().rollCount)
    }

    @Test
    fun get_fabric_rolls_by_ascending_code() = runTest {
        val section1 = inventoryDao.insertSection(testSection("a1"))
        val section2 = inventoryDao.insertSection(testSection("a2"))
        val rolls = listOf(
            testFabricRoll(section1, "a1"),
            testFabricRoll(section1, "j3"),
            testFabricRoll(section1, "c1"),
            testFabricRoll(section2, "a2"),
        )
        rolls.forEach { roll ->
            inventoryDao.insertFabricRoll(roll)
        }

        val result = inventoryDao.getFabricRolls(section1, 10, 0, false).first()

        assertEquals(
            listOf("a1", "c1", "j3"),
            result.map { it.code },
        )
    }

    @Test
    fun get_fabric_rolls_filtered_by_has_remaining_by_ascending_code() = runTest {
        val section1 = inventoryDao.insertSection(testSection("a1"))
        val rolls = listOf(
            testFabricRoll(section1, "a1"),
            testFabricRoll(section1, "j3"),
            testFabricRoll(section1, "c1"),
        )
        val rollIds = mutableListOf<Long>()
        rolls.forEach { roll ->
            rollIds.add(inventoryDao.insertFabricRoll(roll))
        }
        inventoryDao.updateFabricRollRemainingLength(rollIds.first(), 0)

        val result = inventoryDao.getFabricRolls(section1, 10, 0, true).first()

        assertEquals(
            listOf("c1", "j3"),
            result.map { it.code }
        )
    }

    @Test
    fun update_fabric_roll() = runTest {
        val sectionId = inventoryDao.insertSection(testSection("a1"))
        inventoryDao.insertFabricRoll(testFabricRoll(sectionId, "code1"))

        inventoryDao.updateFabricRollRemainingLength(1, 10)

        val result = inventoryDao.getFabricRolls(sectionId, 10, 0, false).first()
        assertEquals(10, result.first().remainingLength)
    }

    @Test
    fun get_release_history_by_descending_order() = runTest {
        val sectionId = inventoryDao.insertSection(testSection("a1"))
        val rollId = inventoryDao.insertFabricRoll(testFabricRoll(sectionId, "code1"))
        val releaseHistories = listOf(
            testReleaseHistory(rollId, 1.0, 0),
            testReleaseHistory(rollId, 2.0, 3),
            testReleaseHistory(rollId, 3.0, 1),
            testReleaseHistory(rollId, 1.0, 2),
        )
        releaseHistories.forEach { history ->
            inventoryDao.insertReleaseHistory(history)
        }

        val result = inventoryDao.getReleaseHistory(rollId).first()

        assertEquals(
            listOf(3L, 2L, 1L, 0L),
            result.map { it.releaseDate.toEpochMilliseconds() },
        )
    }
}

private fun testSection(
    name: String,
) = SectionEntity(
    name = name,
)

private fun testFabricRoll(
    sectionId: Long,
    code: String,
    totalLength: Int = 30,
) = FabricRollEntity(
    sectionId = sectionId,
    code = code,
    color = "",
    remainingLength = totalLength,
    originalLength = totalLength,
)

private fun testReleaseHistory(
    rollId: Long,
    quantity: Double,
    millisSinceEpoch: Long = 0,
    destination: String = "",
) = ReleaseHistoryEntity(
    rollId = rollId,
    quantity = quantity,
    releaseDate = Instant.fromEpochMilliseconds(millisSinceEpoch),
    destination = destination,
)
