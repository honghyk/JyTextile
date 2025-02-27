package com.erp.jytextile.core.data.repository

import com.erp.jytextile.core.data.store.ZonesStore
import com.erp.jytextile.core.data.testdouble.TestInventoryZoneDao
import com.erp.jytextile.core.data.testdouble.TestZoneRemoteDataSource
import com.erp.jytextile.core.database.dao.InventoryZoneDao
import com.erp.jytextile.core.domain.repository.ZoneInventoryRepository
import com.erp.jytextile.core.network.ZoneRemoteDataSource
import com.erp.jytextile.core.network.model.ZoneInsertRequest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ZoneInventoryRepositoryTest {

    private lateinit var testZoneInventoryDao: InventoryZoneDao
    private lateinit var testZoneRemoteDataSource: ZoneRemoteDataSource
    private lateinit var testInventoryRepository: ZoneInventoryRepository

    @BeforeTest
    fun setUp() {
        testZoneInventoryDao = TestInventoryZoneDao()
        testZoneRemoteDataSource = TestZoneRemoteDataSource()

        testInventoryRepository = ZoneInventoryRepositoryImpl(
            testZoneInventoryDao,
            testZoneRemoteDataSource,
            ZonesStore(testZoneInventoryDao, testZoneRemoteDataSource)
        )
    }

    @Test
    fun get_zones_returns_empty_list_when_no_zones_exist() = runTest {
        val result = testInventoryRepository.getZones(0, 10).first()

        assertEquals(emptyList(), result)
    }

    @Test
    fun get_zones_returns_zones_when_zones_exist() = runTest {
        inertZones(30)

        val result = testInventoryRepository.getZones(1, 10).first()

        assertEquals(10, result.size)
    }

    private suspend fun inertZones(count: Int) {
        repeat(count) {
            testZoneRemoteDataSource.upsert(ZoneInsertRequest(name = "Zone $it"))
        }
    }
}
