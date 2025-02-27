package com.erp.jytextile.core.data.store

import com.erp.jytextile.core.data.datasource.local.FabricRollLocalDataSource
import com.erp.jytextile.core.data.datasource.remote.FabricRollRemoteDataSource
import com.erp.jytextile.core.data.util.PagingKey
import com.erp.jytextile.core.data.util.storeBuilder
import com.erp.jytextile.core.domain.model.FabricRoll
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
            if (pagingKey.page == 0) {
                localDataSource.deleteByZoneId(zoneId)
            }
            localDataSource.upsert(response)
        }
    )
).build()
