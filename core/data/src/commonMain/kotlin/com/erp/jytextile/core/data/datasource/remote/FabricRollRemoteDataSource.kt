package com.erp.jytextile.core.data.datasource.remote

import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.core.domain.model.ReleaseHistory
import kotlinx.datetime.Instant

interface FabricRollRemoteDataSource {

    suspend fun getFabricRolls(
        zoneId: Long,
        page: Int,
        pageSize: Int,
    ): List<FabricRoll>

    suspend fun getFabricRoll(rollId: Long): FabricRoll

    suspend fun upsert(fabricRoll: FabricRoll): FabricRoll

    suspend fun upsert(fabricRolls: List<FabricRoll>)

    suspend fun delete(rollId: Long)

    suspend fun deleteByZoneId(zoneId: Long)

    suspend fun releaseFabricRoll(
        rollId: Long,
        quantity: Double,
        buyer: String,
        remark: String,
        releaseAt: Instant,
    ): ReleaseHistory
}
