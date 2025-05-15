package com.erp.trillion.core.data.datasource.local

import com.erp.trillion.core.domain.model.FabricRoll
import kotlinx.coroutines.flow.Flow

interface FabricRollLocalDataSource : EntityLocalDataSource<FabricRoll> {

    fun getFabricRolls(zoneId: Long, page: Int, pageSize: Int): Flow<List<FabricRoll>>

    fun getFabricRoll(rollId: Long): Flow<FabricRoll>

    override suspend fun upsert(entity: FabricRoll): Long

    override suspend fun upsert(entities: List<FabricRoll>)

    override suspend fun delete(id: Long)

    suspend fun deleteByZoneId(zoneId: Long)

    fun search(query: String): Flow<List<FabricRoll>>
}
