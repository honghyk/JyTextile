package com.erp.trillion.core.data.testdouble

import com.erp.trillion.core.data.datasource.remote.ZoneRemoteDataSource
import com.erp.trillion.core.domain.model.Zone
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TestZoneRemoteDataSource : ZoneRemoteDataSource {

    private val zonesStateFlow = MutableStateFlow<List<Zone>>(emptyList())

    var forceError = false

    override suspend fun getZones(page: Int, pageSize: Int): List<Zone> {
        return zonesStateFlow
            .map { zones ->
                zones.subList(
                    fromIndex = page,
                    toIndex = (page + pageSize).coerceAtMost(zones.size)
                )
            }
            .firstOrNull() ?: emptyList()
    }

    override suspend fun upsert(name: String): Zone {
        if (forceError) throw Exception("forced error")

        val new = Zone(
            id = (zonesStateFlow.value.lastOrNull()?.id ?: 0) + 1,
            name = name,
            rollCount = 0,
        )
        zonesStateFlow.update { oldValues ->
            (oldValues + listOf(new))
                .distinctBy { it.id }
        }
        return new
    }

    override suspend fun upsert(names: List<String>) {
        if (forceError) throw Exception("forced error")

        val lastId = zonesStateFlow.value.lastOrNull()?.id ?: 0
        val new = names.mapIndexed { index, name ->
            Zone(
                id = lastId + index + 1,
                name = name,
                rollCount = 0,
            )
        }
        zonesStateFlow.update { oldValues ->
            (oldValues + new)
                .distinctBy { it.id }
        }
    }

    override suspend fun delete(zoneId: Long) {
        if (forceError) throw Exception("forced error")

        zonesStateFlow.update { oldValues ->
            oldValues.filterNot { it.id == zoneId }
        }
    }

    override suspend fun delete(zoneIds: List<Long>) {
        if (forceError) throw Exception("forced error")

        zonesStateFlow.update { oldValues ->
            oldValues.filterNot { it.id in zoneIds }
        }
    }
}
