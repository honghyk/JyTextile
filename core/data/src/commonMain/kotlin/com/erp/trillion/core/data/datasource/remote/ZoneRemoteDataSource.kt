package com.erp.trillion.core.data.datasource.remote

import com.erp.trillion.core.domain.model.Zone

interface ZoneRemoteDataSource {

    suspend fun getZones(
        page: Int,
        pageSize: Int,
    ): List<Zone>

    suspend fun upsert(name: String): Zone

    suspend fun upsert(names: List<String>)

    suspend fun delete(zoneId: Long)

    suspend fun delete(zoneIds: List<Long>)
}
