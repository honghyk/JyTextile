package com.erp.jytextile.core.data.testdouble

import com.erp.jytextile.core.network.ZoneRemoteDataSource
import com.erp.jytextile.core.network.model.FabricRollCountResponse
import com.erp.jytextile.core.network.model.ZoneInsertRequest
import com.erp.jytextile.core.network.model.ZoneResponse
import com.erp.jytextile.core.network.model.ZoneWithRollCountResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TestZoneRemoteDataSource : ZoneRemoteDataSource {

    private val zoneResponsesStateFlow =
        MutableStateFlow<List<ZoneWithRollCountResponse>>(emptyList())

    override suspend fun getZones(page: Int, pageSize: Int): List<ZoneWithRollCountResponse> {
        return zoneResponsesStateFlow
            .map { zones ->
                zones.subList(
                    fromIndex = page,
                    toIndex = (page + pageSize).coerceAtMost(zones.size)
                )
            }
            .firstOrNull() ?: emptyList()
    }

    override suspend fun upsert(request: ZoneInsertRequest): ZoneResponse {
        val new = ZoneWithRollCountResponse(
            id = (zoneResponsesStateFlow.value.lastOrNull()?.id ?: 0) + 1,
            name = request.name,
            rollCount = listOf(FabricRollCountResponse(0)),
        )
        zoneResponsesStateFlow.update { oldValues ->
            (oldValues + listOf(new))
                .distinctBy { it.id }
        }
        return ZoneResponse(new.id, new.name)
    }

    override suspend fun upsert(requests: List<ZoneInsertRequest>) {
        val lastId = zoneResponsesStateFlow.value.lastOrNull()?.id ?: 0
        val new = requests.mapIndexed { index, request ->
            ZoneWithRollCountResponse(
                id = lastId + index + 1,
                name = request.name,
                rollCount = listOf(FabricRollCountResponse(0)),
            )
        }
        zoneResponsesStateFlow.update { oldValues ->
            (oldValues + new)
                .distinctBy { it.id }
        }
    }

    override suspend fun delete(zoneId: Long) {
        zoneResponsesStateFlow.update { oldValues ->
            oldValues.filterNot { it.id == zoneId }
        }
    }

    override suspend fun delete(zoneIds: List<Long>) {
        zoneResponsesStateFlow.update { oldValues ->
            oldValues.filterNot { it.id in zoneIds }
        }
    }

    override suspend fun getZoneCount(): Int {
        return zoneResponsesStateFlow.value.size
    }
}
