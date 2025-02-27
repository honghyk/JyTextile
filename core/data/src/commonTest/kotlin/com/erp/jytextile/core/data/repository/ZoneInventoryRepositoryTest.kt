package com.erp.jytextile.core.data.repository

import com.erp.jytextile.core.data.datasource.remote.ZoneRemoteDataSource
import com.erp.jytextile.core.data.store.ZonesStore
import com.erp.jytextile.core.data.testdouble.TestZoneLocalDataSource
import com.erp.jytextile.core.data.testdouble.TestZoneRemoteDataSource
import com.erp.jytextile.core.database.datasource.ZoneLocalDataSource
import com.erp.jytextile.core.domain.repository.ZoneInventoryRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ZoneInventoryRepositoryTest {

    private lateinit var testZoneLocalDataSource: ZoneLocalDataSource
    private lateinit var testZoneRemoteDataSource: ZoneRemoteDataSource
    private lateinit var testInventoryRepository: ZoneInventoryRepository

    @BeforeTest
    fun setUp() {
        testZoneLocalDataSource = TestZoneLocalDataSource()
        testZoneRemoteDataSource = TestZoneRemoteDataSource()

        testInventoryRepository = ZoneInventoryRepositoryImpl(
            testZoneLocalDataSource,
            testZoneRemoteDataSource,
            ZonesStore(testZoneLocalDataSource, testZoneRemoteDataSource)
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
            testZoneRemoteDataSource.upsert(name = "Zone $it")
        }
    }
}
