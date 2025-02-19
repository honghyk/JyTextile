package com.erp.jytextile.core.data.repository

import com.erp.jytextile.core.data.testdouble.TestInventoryDao
import com.erp.jytextile.core.database.dao.InventoryDao
import com.erp.jytextile.core.database.model.ZoneEntity
import com.erp.jytextile.core.domain.repository.ZoneInventoryRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ZoneInventoryRepositoryTest {

    private lateinit var testInventoryDao: InventoryDao
    private lateinit var testInventoryRepository: ZoneInventoryRepository

    @BeforeTest
    fun setUp() {
        testInventoryDao = TestInventoryDao()
        testInventoryRepository = ZoneInventoryRepositoryImpl(testInventoryDao)
    }

    @Test
    fun get_section_page_count_is_exactly_divisible_by_page_size() = runTest {
        insertSections(20)

        val result = testInventoryRepository.getZonePage().first()

        assertEquals(1, result)
    }

    @Test
    fun get_section_page_count_is_not_exactly_divisible_by_page_size() = runTest {
        insertSections(50)

        val result = testInventoryRepository.getZonePage().first()

        assertEquals(3, result)
    }

    private suspend fun insertSections(count: Int) {
        repeat(count) {
            testInventoryDao.insertZone(ZoneEntity(id = it.toLong(), name = "Section $it"))
        }
    }
}
