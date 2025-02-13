package com.erp.jytextile.core.data.repository

import com.erp.jytextile.core.data.testdouble.TestInventoryDao
import com.erp.jytextile.core.database.dao.InventoryDao
import com.erp.jytextile.core.database.model.SectionEntity
import com.erp.jytextile.core.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class InventoryRepositoryTest {

    private lateinit var testInventoryDao: InventoryDao
    private lateinit var testInventoryRepository: InventoryRepository

    @BeforeTest
    fun setUp() {
        testInventoryDao = TestInventoryDao()
        testInventoryRepository = InventoryRepositoryImpl(testInventoryDao)
    }

    @Test
    fun get_section_page_count_is_exactly_divisible_by_page_size() = runTest {
        insertSections(20)

        val result = testInventoryRepository.getSectionPages().first()

        assertEquals(1, result)
    }

    @Test
    fun get_section_page_count_is_not_exactly_divisible_by_page_size() = runTest {
        insertSections(50)

        val result = testInventoryRepository.getSectionPages().first()

        assertEquals(3, result)
    }

    private suspend fun insertSections(count: Int) {
        repeat(count) {
            testInventoryDao.insertSection(SectionEntity(id = it.toLong(), name = "Section $it"))
        }
    }
}
