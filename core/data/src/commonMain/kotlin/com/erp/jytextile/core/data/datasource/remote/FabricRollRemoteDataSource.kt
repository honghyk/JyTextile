package com.erp.jytextile.core.data.datasource.remote

import com.erp.jytextile.core.domain.model.FabricRoll

interface FabricRollRemoteDataSource {

    suspend fun getFabricRolls(
        zoneId: Long,
        page: Int,
        pageSize: Int,
    ): List<FabricRoll>

    suspend fun upsert(fabricRolls: List<FabricRoll>): FabricRoll

    suspend fun delete(rollId: Long)

    suspend fun deleteByZoneId(zoneId: Long)
}
