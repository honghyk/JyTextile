package com.erp.jytextile.core.network.datasource

import com.erp.jytextile.core.data.datasource.remote.FabricRollRemoteDataSource
import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.core.network.Tables
import com.erp.jytextile.core.network.model.FabricRollResponse
import com.erp.jytextile.core.network.model.ReleaseFabricRollRequest
import com.erp.jytextile.core.network.model.toDomain
import com.erp.jytextile.core.network.model.toRequest
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.datetime.Instant
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
        return client
            .from(Tables.FABRIC_ROLLS)
            .select(Columns.raw(fabricRollQuery)) {
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

    override suspend fun getFabricRoll(rollId: Long): FabricRoll {
        return client
            .from(Tables.FABRIC_ROLLS)
            .select(Columns.raw(fabricRollQuery)) {
                filter { eq("id", rollId) }
            }
            .decodeSingle<FabricRollResponse>()
            .toDomain()
    }

    override suspend fun upsert(fabricRoll: FabricRoll): FabricRoll {
        return client
            .from(Tables.FABRIC_ROLLS)
            .upsert(fabricRoll.toRequest()) {
                select(Columns.raw(fabricRollQuery))
            }
            .decodeSingle<FabricRollResponse>()
            .toDomain()
    }

    override suspend fun upsert(fabricRolls: List<FabricRoll>) {
        client
            .from(Tables.FABRIC_ROLLS)
            .insert(fabricRolls)
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

    override suspend fun releaseFabricRoll(
        rollId: Long,
        quantity: Double,
        buyer: String,
        remark: String,
        releaseAt: Instant
    ) {
        client
            .from(Tables.RELEASE_HISTORIES)
            .insert(
                ReleaseFabricRollRequest(
                    rollId = rollId,
                    quantity = quantity,
                    buyer = buyer,
                    remark = remark.ifEmpty { null },
                    releaseAt = releaseAt,
                )
            )
    }

    private val fabricRollQuery = """
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
}
