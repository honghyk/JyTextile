package com.erp.jytextile.core.domain.repository

import com.erp.jytextile.core.domain.model.Zone
import kotlinx.coroutines.flow.Flow

interface ZoneInventoryRepository {

    suspend fun addZone(name: String)

    fun getZones(
        page: Int,
    ): Flow<List<Zone>>

    fun getZonesCount(): Flow<Int>

    fun getZonePage(): Flow<Int>
}
