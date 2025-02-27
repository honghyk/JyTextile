package com.erp.jytextile.core.network.datasource

import com.erp.jytextile.core.data.datasource.remote.FabricRollRemoteDataSource
import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.core.network.Tables
import com.erp.jytextile.core.network.model.FabricRollResponse
import com.erp.jytextile.core.network.model.toDomain
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import me.tatarka.inject.annotations.Inject

@Inject
class FabricRollRemoteDataSourceImpl(
    private val client: SupabaseClient,
) : FabricRollRemoteDataSource {

    override suspend fun getFabricRolls(
        zoneId: Long,
        page: Int,
        pageSize: Int
    ): List<FabricRoll> {
        val columns = Columns.raw(
            """
                    id,
                    ${Tables.ZONES}(id, name),
                    item_no,
                    order_no,
                    color,
                    factory,
                    finish,
                    remaining_quantity,
                    original_quantity,
                    remark
                """.trimIndent()
        )

        return client
            .from(Tables.FABRIC_ROLLS)
            .select(columns) {
                filter { eq("zone_id", zoneId) }
                order(column = "id", order = Order.ASCENDING)
                range(
                    from = (page * pageSize).toLong(),
                    to = (page * pageSize + pageSize - 1).toLong()
                )
            }
            .decodeList<FabricRollResponse>()
            .map { it.toDomain() }
    }

    override suspend fun upsert(fabricRolls: List<FabricRoll>): FabricRoll {
        return client
            .from(Tables.FABRIC_ROLLS)
            .upsert(fabricRolls) {
                select()
            }
            .decodeSingle<FabricRollResponse>()
            .toDomain()
    }

    override suspend fun delete(rollId: Long) {
        client
            .from(Tables.FABRIC_ROLLS)
            .delete {
                filter { FabricRollResponse::id eq rollId }
            }
    }

    override suspend fun deleteByZoneId(zoneId: Long) {
        client
            .from(Tables.FABRIC_ROLLS)
            .delete {
                filter { eq("zone_id", zoneId) }
            }
    }
}
