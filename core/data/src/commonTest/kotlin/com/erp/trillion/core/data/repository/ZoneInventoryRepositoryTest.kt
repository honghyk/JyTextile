package com.erp.trillion.core.data.repository

import com.erp.trillion.core.data.store.ZonesStore
import com.erp.trillion.core.data.testdouble.TestZoneLocalDataSource
import com.erp.trillion.core.data.testdouble.TestZoneRemoteDataSource
import com.erp.trillion.core.domain.repository.ZoneInventoryRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ZoneInventoryRepositoryTest {

    private lateinit var testZoneLocalDataSource: TestZoneLocalDataSource
    private lateinit var testZoneRemoteDataSource: TestZoneRemoteDataSource
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

    @Test
    fun add_zone_to_local_when_add_to_remote_success() = runTest {
        testZoneRemoteDataSource.forceError = true
        runCatching { testInventoryRepository.addZone("Zone 1") }

        val result = testInventoryRepository.getZones(0, 10).first()

        assertEquals(0, result.size)
    }

    private suspend fun inertZones(count: Int) {
        repeat(count) {
            testZoneRemoteDataSource.upsert(name = "Zone $it")
        }
    }
}
