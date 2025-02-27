package com.erp.jytextile.core.data.repository

import com.erp.jytextile.core.data.datasource.local.ZoneLocalDataSource
import com.erp.jytextile.core.data.datasource.remote.ZoneRemoteDataSource
import com.erp.jytextile.core.data.store.PagingKey
import com.erp.jytextile.core.data.store.ZonesStore
import com.erp.jytextile.core.domain.model.Zone
import com.erp.jytextile.core.domain.repository.ZoneInventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse

@Inject
class ZoneInventoryRepositoryImpl(
    private val zoneLocalDataSource: ZoneLocalDataSource,
    private val zoneRemoteDataSource: ZoneRemoteDataSource,
    private val zonesStore: ZonesStore,
) : ZoneInventoryRepository {

    override suspend fun addZone(name: String) {
        zoneRemoteDataSource.upsert(name).also {
            zoneLocalDataSource.upsert(it)
        }
    }

    override fun getZones(
        page: Int,
        pageSize: Int,
    ): Flow<List<Zone>> {
        return zonesStore
            .stream(StoreReadRequest.cached(PagingKey(page, pageSize), true))
            .filter { it is StoreReadResponse.Data }
            .map { it.requireData() }
    }

    override suspend fun deleteZones(zoneIds: List<Long>) {
        zoneRemoteDataSource.delete(zoneIds)
        zoneLocalDataSource.delete(zoneIds)
    }
}
