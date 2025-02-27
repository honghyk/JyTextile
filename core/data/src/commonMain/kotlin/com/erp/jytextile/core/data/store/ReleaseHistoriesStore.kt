package com.erp.jytextile.core.data.store

import com.erp.jytextile.core.data.datasource.remote.ReleaseHistoryRemoteDataSource
import com.erp.jytextile.core.database.datasource.ReleaseHistoryLocalDataSource
import com.erp.jytextile.core.domain.model.ReleaseHistory
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store

@Inject
class ReleaseHistoriesStore(
    private val localDataSource: ReleaseHistoryLocalDataSource,
    private val releaseHistoryRemoteDataSource: ReleaseHistoryRemoteDataSource,
) : Store<Long, List<ReleaseHistory>> by storeBuilder(
    fetcher = Fetcher.of { rollId ->
        releaseHistoryRemoteDataSource.getReleaseHistories(rollId)
    },
    sourceOfTruth = SourceOfTruth.of<Long, List<ReleaseHistory>, List<ReleaseHistory>>(
        reader = { rollId ->
          localDataSource.getReleaseHistories(rollId)
        },
        writer = { rollId, releaseHistories ->
            localDataSource.deleteReleaseHistories(rollId)
            localDataSource.insertReleaseHistory(releaseHistories)
        }
    )
).build()
