package com.erp.jytextile.core.data.repository

import com.erp.jytextile.core.data.datasource.remote.ReleaseHistoryRemoteDataSource
import com.erp.jytextile.core.data.store.ReleaseHistoriesStore
import com.erp.jytextile.core.domain.model.ReleaseHistory
import com.erp.jytextile.core.domain.repository.ReleaseHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse

@Inject
class ReleaseHistoryRepositoryImpl(
    private val releaseHistoryRemoteDataSource: ReleaseHistoryRemoteDataSource,
    private val releaseHistoryStore: ReleaseHistoriesStore,
) : ReleaseHistoryRepository {

    override fun getReleaseHistories(rollId: Long): Flow<List<ReleaseHistory>> {
        return releaseHistoryStore
            .stream(StoreReadRequest.cached(rollId, true))
            .filter { it is StoreReadResponse.Data }
            .map { it.requireData() }
    }

    override suspend fun removeReleaseHistories(ids: List<Long>) {
        releaseHistoryRemoteDataSource.deleteReleaseHistories(ids)
    }
}
