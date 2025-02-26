package com.erp.jytextile.core.data.repository

import com.erp.jytextile.core.data.store.PagingKey
import com.erp.jytextile.core.data.store.ZonesStore
import com.erp.jytextile.core.database.dao.InventoryZoneDao
import com.erp.jytextile.core.database.model.toDomain
import com.erp.jytextile.core.database.model.toEntity
import com.erp.jytextile.core.domain.model.Zone
import com.erp.jytextile.core.domain.repository.ZoneInventoryRepository
import com.erp.jytextile.core.network.ZoneRemoteDataSource
import com.erp.jytextile.core.network.model.ZoneInsertRequest
import com.erp.jytextile.core.network.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse

@Inject
class ZoneInventoryRepositoryImpl(
    private val inventoryDao: InventoryZoneDao,
    private val zoneRemoteDataSource: ZoneRemoteDataSource,
    private val zonesStore: ZonesStore,
) : ZoneInventoryRepository {

    override suspend fun addZone(name: String) {
        zoneRemoteDataSource.upsert(ZoneInsertRequest(name)).also {
            inventoryDao.insert(it.toDomain().toEntity())
        }
    }

    override fun getZones(
        page: Int,
        pageSize: Int,
    ): Flow<List<Zone>> {
        return zonesStore
            .stream(StoreReadRequest.cached(PagingKey(page, pageSize), true))
            .filter { it is StoreReadResponse.Data }
            .map {
                it.requireData().map { zones -> zones.toDomain() }
            }
    }

    override fun getZonesCount(): Flow<Int> {
        TODO("Implement synchronization with the local database")
    }

    override suspend fun deleteZones(zoneIds: List<Long>) {
        zoneRemoteDataSource.delete(zoneIds)
        inventoryDao.delete(zoneIds)
    }
}
