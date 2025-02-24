package com.erp.jytextile.core.data.repository

import com.erp.jytextile.core.data.testdouble.TestInventoryDao
import com.erp.jytextile.core.database.dao.InventoryDao
import com.erp.jytextile.core.database.model.ZoneEntity
import com.erp.jytextile.core.domain.model.FabricRollInsertion
import com.erp.jytextile.core.domain.model.LengthUnit
import com.erp.jytextile.core.domain.repository.RollInventoryRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RollInventoryRepositoryTest {

    private lateinit var testInventoryDao: InventoryDao
    private lateinit var testInventoryRepository: RollInventoryRepository

    @BeforeTest
    fun setUp() {
        testInventoryDao = TestInventoryDao()
        testInventoryRepository = RollInventoryRepositoryImpl(testInventoryDao)
    }

    @Test
    fun get_existing_fabric_roll() = runTest {
        testInventoryDao.insertZone(ZoneEntity(id = 0, ""))
        testInventoryRepository.upsertFabricRoll(0, testFabricRollInsertion(0, "item1"))
        testInventoryRepository.upsertFabricRoll(0, testFabricRollInsertion(1, "item2"))

        val result = testInventoryRepository.getRoll(0).firstOrNull()

        assertEquals(
            "item1",
            result?.itemNo,
        )
    }
}

private fun testFabricRollInsertion(
    id: Long,
    itemNo: String = "",
) = FabricRollInsertion(
    id = id,
    itemNo = itemNo,
    orderNo = "",
    color = "",
    factory = "",
    finish = "",
    quantity = 20.0,
    remark = "",
    lengthUnit = LengthUnit.METER,
)
