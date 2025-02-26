package com.erp.jytextile.core.network

import com.erp.jytextile.core.network.model.ZoneInsertRequest
import com.erp.jytextile.core.network.model.ZoneResponse
import com.erp.jytextile.core.network.model.ZoneWithRollCountResponse
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Count
import io.github.jan.supabase.postgrest.query.Order
import me.tatarka.inject.annotations.Inject

interface ZoneRemoteDataSource {

    suspend fun getZones(
        page: Int,
        pageSize: Int,
    ): List<ZoneWithRollCountResponse>

    suspend fun upsert(request: ZoneInsertRequest): ZoneResponse

    suspend fun upsert(requests: List<ZoneInsertRequest>)

    suspend fun delete(zoneId: Long)

    suspend fun delete(zoneIds: List<Long>)

    suspend fun getZoneCount(): Int
}

@Inject
class ZoneRemoteDataSourceImpl(
    private val client: SupabaseClient,
) : ZoneRemoteDataSource {

    override suspend fun getZones(
        page: Int,
        pageSize: Int,
    ): List<ZoneWithRollCountResponse> {
        return client
            .from(Tables.ZONES)
            .select(Columns.raw("id, name, fabric_rolls(count)")) {
                order(column = "id", order = Order.ASCENDING)
                range(
                    from = (page * pageSize).toLong(),
                    to = (page * pageSize + pageSize - 1).toLong()
                )
            }
            .decodeList<ZoneWithRollCountResponse>()
    }

    override suspend fun upsert(request: ZoneInsertRequest): ZoneResponse {
        return client
            .from(Tables.ZONES)
            .upsert(request) {
                select()
            }
            .decodeSingle<ZoneResponse>()
    }

    override suspend fun upsert(requests: List<ZoneInsertRequest>) {
        client
            .from(Tables.ZONES)
            .insert(requests)
    }

    override suspend fun delete(zoneId: Long) {
        client
            .from(Tables.ZONES)
            .delete {
                filter { ZoneResponse::id eq zoneId }
            }
    }

    override suspend fun delete(zoneIds: List<Long>) {
        client
            .from(Tables.ZONES)
            .delete {
                filter {
                    isIn("id", zoneIds)
                }
            }
    }

    override suspend fun getZoneCount(): Int {
        return client
            .from(Tables.ZONES)
            .select {
                count(Count.EXACT)
            }
            .countOrNull()!!
            .toInt()
    }
}
