package com.erp.jytextile.core.data.store

import com.erp.jytextile.core.data.datasource.remote.ReleaseHistoryRemoteDataSource
import com.erp.jytextile.core.domain.model.ReleaseHistory
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.Store

@Inject
class ReleaseHistoriesStore(
    private val releaseHistoryRemoteDataSource: ReleaseHistoryRemoteDataSource,
) : Store<Long, List<ReleaseHistory>> by storeBuilder<Long, List<ReleaseHistory>>(
    fetcher = Fetcher.of { rollId ->
        releaseHistoryRemoteDataSource.getReleaseHistories(rollId)
    },
).build()
