package com.erp.jytextile.core.data.datasource.local

import com.erp.jytextile.core.domain.model.FabricRoll
import kotlinx.coroutines.flow.Flow

interface FabricRollLocalDataSource {

    fun getFabricRolls(zoneId: Long, page: Int, pageSize: Int): Flow<List<FabricRoll>>

    fun getFabricRoll(rollId: Long): Flow<FabricRoll>

    suspend fun upsert(fabricRoll: FabricRoll)

    suspend fun upsert(fabricRolls: List<FabricRoll>)

    suspend fun delete(rollId: Long)

    suspend fun deleteByZoneId(zoneId: Long)

    fun search(query: String): Flow<List<FabricRoll>>
}
