package com.erp.trillion.core.network.datasource

import com.erp.trillion.core.data.datasource.remote.ZoneRemoteDataSource
import com.erp.trillion.core.domain.model.Zone
import com.erp.trillion.core.network.Tables
import com.erp.trillion.core.network.model.ZoneInsertRequest
import com.erp.trillion.core.network.model.ZoneResponse
import com.erp.trillion.core.network.model.ZoneWithRollCountResponse
import com.erp.trillion.core.network.model.toDomain
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import me.tatarka.inject.annotations.Inject

@Inject
class ZoneRemoteDataSourceImpl(
    private val client: SupabaseClient,
) : ZoneRemoteDataSource {

    override suspend fun getZones(
        page: Int,
        pageSize: Int,
    ): List<Zone> {
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
            .map { it.toDomain() }
    }

    override suspend fun upsert(name: String): Zone {
        return client
            .from(Tables.ZONES)
            .upsert(ZoneInsertRequest(name)) {
                select()
            }
            .decodeSingle<ZoneResponse>()
            .toDomain()
    }

    override suspend fun upsert(names: List<String>) {
        client
            .from(Tables.ZONES)
            .insert(names.map { ZoneInsertRequest(it) })
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
}
