package com.erp.jytextile.core.data.store

import com.erp.jytextile.core.database.dao.InventoryZoneDao
import com.erp.jytextile.core.database.model.ZoneEntity
import com.erp.jytextile.core.database.model.toEntity
import com.erp.jytextile.core.network.ZoneRemoteDataSource
import com.erp.jytextile.core.network.model.ZoneWithRollCountResponse
import com.erp.jytextile.core.network.model.toDomain
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store

@Inject
class ZonesStore(
    private val dao: InventoryZoneDao,
    private val remoteDataSource: ZoneRemoteDataSource,
) : Store<PagingKey, List<ZoneEntity>> by storeBuilder<PagingKey, List<ZoneWithRollCountResponse>, List<ZoneEntity>>(
    fetcher = Fetcher.of { key ->
        remoteDataSource.getZones(key.page, key.pageSize * 2)
    },
    sourceOfTruth = SourceOfTruth.of(
        reader = { key ->
            dao.getZones(offset = key.offset, limit = key.pageSize)
        },
        writer = { key, response ->
            if (key.page == 0) {
                dao.deleteAll()
            }
            dao.upsert(response.map { it.toDomain().toEntity() })
        },
        delete = { key ->
            dao.deletePage(offset = key.offset, limit = key.pageSize)
        },
        deleteAll = { dao.deleteAll() },
    )
).build()
