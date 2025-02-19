package com.erp.jytextile.core.domain.repository

import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.core.domain.model.FabricRollInsertion
import com.erp.jytextile.core.domain.model.LengthUnit
import com.erp.jytextile.core.domain.model.ReleaseHistory
import com.erp.jytextile.core.domain.model.Zone
import kotlinx.coroutines.flow.Flow

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

    suspend fun addFabricRoll(
        zoneId: Long,
        rollInsertion: FabricRollInsertion,
    )

    suspend fun addFabricRoll(
        zoneName: String,
        rollInsertion: FabricRollInsertion,
    )

    suspend fun removeFabricRoll(rollId: Long)

    fun getFabricRollsCount(zoneId: Long): Flow<Int>

    fun getFabricRollsPage(zoneId: Long): Flow<Int>

    suspend fun releaseFabricRoll(
        rollId: Long,
        orderNo: String,
        quantity: Double,
        lengthUnit: LengthUnit,
        buyer: String,
        releaseDate: String,
    )

    fun getRoll(rollId: Long): Flow<FabricRoll>

    fun getReleaseHistories(
        rollId: Long,
        page: Int,
    ): Flow<List<ReleaseHistory>>

    fun getReleaseHistoriesPage(rollId: Long): Flow<Int>
}
