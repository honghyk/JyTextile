package com.erp.jytextile.core.data.datasource.local

import com.erp.jytextile.core.domain.model.Zone
import kotlinx.coroutines.flow.Flow

interface ZoneLocalDataSource {

    fun getZones(
        page: Int,
        pageSize: Int,
    ): Flow<List<Zone>>

    suspend fun upsert(zone: Zone): Zone

    suspend fun upsert(names: List<Zone>)

    suspend fun delete(zoneId: Long)

    suspend fun delete(zoneIds: List<Long>)
}
