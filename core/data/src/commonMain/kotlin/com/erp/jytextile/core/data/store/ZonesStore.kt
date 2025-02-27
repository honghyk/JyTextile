package com.erp.jytextile.core.data.store

import com.erp.jytextile.core.data.datasource.local.ZoneLocalDataSource
import com.erp.jytextile.core.data.datasource.remote.ZoneRemoteDataSource
import com.erp.jytextile.core.data.util.PagingKey
import com.erp.jytextile.core.data.util.storeBuilder
import com.erp.jytextile.core.data.util.syncerForEntity
import com.erp.jytextile.core.domain.model.Zone
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
        remoteDataSource.getZones(key.page, key.pageSize)
    },
    sourceOfTruth = SourceOfTruth.of<PagingKey, List<Zone>, List<Zone>>(
        reader = { key ->
            localDataSource.getZones(page = key.page, pageSize = key.pageSize)
        },
        writer = { key, response ->
            syncerForEntity(localDataSource)
                .sync(
                    currentValues = localDataSource.getZones(key.page, key.pageSize).first(),
                    networkValues = response,
                )
        },
    )
).build()
