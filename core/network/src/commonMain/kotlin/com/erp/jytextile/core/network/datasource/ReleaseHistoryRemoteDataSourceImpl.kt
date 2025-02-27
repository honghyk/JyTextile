package com.erp.jytextile.core.network.datasource

import com.erp.jytextile.core.data.datasource.remote.ReleaseHistoryRemoteDataSource
import com.erp.jytextile.core.domain.model.ReleaseHistory
import com.erp.jytextile.core.network.Tables
import com.erp.jytextile.core.network.model.ReleaseHistoryResponse
import com.erp.jytextile.core.network.model.toDomain
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import me.tatarka.inject.annotations.Inject

@Inject
class ReleaseHistoryRemoteDataSourceImpl(
    private val client: SupabaseClient,
) : ReleaseHistoryRemoteDataSource {

    override suspend fun getReleaseHistories(rollId: Long): List<ReleaseHistory> {
        return client
            .from(Tables.RELEASE_HISTORIES)
            .select {
                filter { eq("roll_id", rollId) }
                order(column = "release_at", order = Order.DESCENDING)
            }
            .decodeList<ReleaseHistoryResponse>()
            .map(ReleaseHistoryResponse::toDomain)
    }

    override suspend fun deleteReleaseHistories(ids: List<Long>) {
        client
            .from(Tables.RELEASE_HISTORIES)
            .delete {
                filter { isIn("id", ids) }
            }
    }
}
