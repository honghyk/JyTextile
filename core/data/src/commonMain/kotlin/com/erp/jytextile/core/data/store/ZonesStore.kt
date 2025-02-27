package com.erp.jytextile.core.data.store

import com.erp.jytextile.core.data.datasource.remote.ZoneRemoteDataSource
import com.erp.jytextile.core.database.datasource.ZoneLocalDataSource
import com.erp.jytextile.core.domain.model.Zone
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
            if (key.page == 0) {
                localDataSource.deleteAll()
            }
            localDataSource.upsert(response)
        },
        delete = { key ->
            localDataSource.deletePage(page = key.page, pageSize = key.pageSize)
        },
        deleteAll = { localDataSource.deleteAll() },
    )
).build()
