package com.erp.jytextile.core.data.datasource.local

import com.erp.jytextile.core.domain.model.Zone
import kotlinx.coroutines.flow.Flow

interface ZoneLocalDataSource: EntityLocalDataSource<Zone> {

    fun getZones(
        page: Int,
        pageSize: Int,
    ): Flow<List<Zone>>

    override suspend fun upsert(entity: Zone): Long

    override suspend fun upsert(entities: List<Zone>)

    override suspend fun delete(entity: Zone)

    suspend fun delete(zoneIds: List<Long>)

    suspend fun deleteAll()

    suspend fun deletePage(page: Int, pageSize: Int)
}
