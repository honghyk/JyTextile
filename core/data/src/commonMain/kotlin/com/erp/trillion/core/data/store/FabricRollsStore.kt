package com.erp.trillion.core.data.store

import com.erp.trillion.core.data.datasource.local.FabricRollLocalDataSource
import com.erp.trillion.core.data.datasource.remote.FabricRollRemoteDataSource
import com.erp.trillion.core.data.util.PagingKey
import com.erp.trillion.core.data.util.storeBuilder
import com.erp.trillion.core.data.util.syncerForEntity
import com.erp.trillion.core.domain.model.FabricRoll
import kotlinx.coroutines.flow.first
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store

@Inject
class FabricRollsStore(
    private val localDataSource: FabricRollLocalDataSource,
    private val remoteDataSource: FabricRollRemoteDataSource,
) : Store<Pair<Long, PagingKey>, List<FabricRoll>> by storeBuilder(
    fetcher = Fetcher.of { (zoneId, pagingKey) ->
        remoteDataSource.getFabricRolls(
            zoneId = zoneId,
            page = pagingKey.page,
            pageSize = pagingKey.pageSize * 2
        )
    },
    sourceOfTruth = SourceOfTruth.of<Pair<Long, PagingKey>, List<FabricRoll>, List<FabricRoll>>(
        reader = { (zoneId, pagingKey) ->
            localDataSource.getFabricRolls(
                zoneId = zoneId,
                page = pagingKey.page,
                pageSize = pagingKey.pageSize
            )
        },
        writer = { (zoneId, pagingKey), response ->
            syncerForEntity(localDataSource)
                .sync(
                    currentValues = localDataSource
                        .getFabricRolls(
                            zoneId = zoneId,
                            page = pagingKey.page,
                            pageSize = pagingKey.pageSize * 2
                        )
                        .first(),
                    networkValues = response
                )
        }
    )
).build()
