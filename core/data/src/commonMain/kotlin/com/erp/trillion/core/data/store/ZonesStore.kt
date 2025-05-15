package com.erp.trillion.core.data.store

import com.erp.trillion.core.data.datasource.local.ZoneLocalDataSource
import com.erp.trillion.core.data.datasource.remote.ZoneRemoteDataSource
import com.erp.trillion.core.data.util.PagingKey
import com.erp.trillion.core.data.util.storeBuilder
import com.erp.trillion.core.data.util.syncerForEntity
import com.erp.trillion.core.domain.model.Zone
import kotlinx.coroutines.flow.first
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store

@Inject
class ZonesStore(
    private val localDataSource: ZoneLocalDataSource,
    private val remoteDataSource: ZoneRemoteDataSource,
) : Store<PagingKey, List<Zone>> by storeBuilder(
    fetcher = Fetcher.of { key ->
        remoteDataSource.getZones(key.page, key.pageSize * 2)
    },
    sourceOfTruth = SourceOfTruth.of<PagingKey, List<Zone>, List<Zone>>(
        reader = { key ->
            localDataSource.getZones(page = key.page, pageSize = key.pageSize)
        },
        writer = { key, response ->
            syncerForEntity(localDataSource)
                .sync(
                    currentValues = localDataSource
                        .getZones(
                            page = key.page,
                            pageSize = key.pageSize * 2
                        )
                        .first(),
                    networkValues = response,
                )
        },
    )
).build()
