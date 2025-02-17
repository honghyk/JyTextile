package com.erp.jytextile.core.domain.repository

import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.core.domain.model.Zone
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

interface InventoryRepository {

    suspend fun addZone(name: String)

    fun getZones(
        page: Int,
    ): Flow<List<Zone>>

    fun getZonesCount(): Flow<Int>

    fun getZonePage(): Flow<Int>

    fun getFabricRolls(
        zoneId: Long,
        page: Int,
        filterHasRemaining: Boolean
    ): Flow<List<FabricRoll>>

    suspend fun addFabricRoll(fabricRoll: FabricRoll)

    suspend fun removeFabricRoll(rollId: Long)

    suspend fun releaseFabricRoll(
        rollId: Long,
        quantity: Double,
        destination: String,
        releaseDate: Instant = Clock.System.now(),
    )
}
