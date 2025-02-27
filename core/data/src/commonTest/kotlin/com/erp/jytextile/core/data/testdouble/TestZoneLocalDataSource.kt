package com.erp.jytextile.core.data.testdouble

import com.erp.jytextile.core.data.datasource.local.ZoneLocalDataSource
import com.erp.jytextile.core.domain.model.Zone
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TestZoneLocalDataSource : ZoneLocalDataSource {

    private val zonesStateFlow = MutableStateFlow<List<Zone>>(emptyList())

    override fun getZones(page: Int, pageSize: Int): Flow<List<Zone>> {
        return zonesStateFlow
            .map { zones ->
                zones.subList(
                    fromIndex = page,
                    toIndex = (page + pageSize).coerceAtMost(zones.size)
                )
            }
    }

    override suspend fun upsert(entity: Zone): Long {
        zonesStateFlow.update { oldValues ->
            (oldValues + listOf(entity))
                .distinctBy { it.id }
        }
        return entity.id
    }

    override suspend fun upsert(entities: List<Zone>) {
        zonesStateFlow.update { oldValues ->
            (oldValues + entities)
                .distinctBy { it.id }
        }
    }

    override suspend fun delete(id: Long) {
        zonesStateFlow.update { oldValues ->
            oldValues.filterNot { it.id == id }
        }
    }

    override suspend fun delete(zoneIds: List<Long>) {
        zonesStateFlow.update { oldValues ->
            oldValues.filterNot { it.id in zoneIds }
        }
    }

    override suspend fun deleteAll() {
        zonesStateFlow.value = emptyList()
    }

    override suspend fun deletePage(page: Int, pageSize: Int) {
        val offset = page * pageSize
        zonesStateFlow.update { oldValues ->
            oldValues.subList(0, offset) + oldValues.subList(offset + pageSize, oldValues.size)
        }
    }
}
