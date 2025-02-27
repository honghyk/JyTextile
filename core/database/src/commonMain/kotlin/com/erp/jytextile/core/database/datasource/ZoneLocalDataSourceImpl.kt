package com.erp.jytextile.core.database.datasource

import com.erp.jytextile.core.data.datasource.local.ZoneLocalDataSource
import com.erp.jytextile.core.database.dao.InventoryZoneDao
import com.erp.jytextile.core.database.model.ZoneEntity
import com.erp.jytextile.core.database.model.toDomain
import com.erp.jytextile.core.database.model.toEntity
import com.erp.jytextile.core.domain.model.Zone
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class ZoneLocalDataSourceImpl(
    private val inventoryZoneDao: InventoryZoneDao,
) : ZoneLocalDataSource {

    override fun getZones(page: Int, pageSize: Int): Flow<List<Zone>> {
        return inventoryZoneDao
            .getZones(
                offset = page * pageSize,
                limit = pageSize
            )
            .map { zones -> zones.map(ZoneEntity::toDomain) }
    }

    override suspend fun upsert(zone: Zone) {
        inventoryZoneDao.upsert(zone.toEntity())
    }

    override suspend fun upsert(zones: List<Zone>) {
        inventoryZoneDao.upsert(zones.map { it.toEntity() })
    }

    override suspend fun delete(zoneId: Long) {
        inventoryZoneDao.delete(zoneId)
    }

    override suspend fun delete(zoneIds: List<Long>) {
        inventoryZoneDao.delete(zoneIds)
    }

    override suspend fun deleteAll() {
        inventoryZoneDao.deleteAll()
    }

    override suspend fun deletePage(page: Int, pageSize: Int) {
        inventoryZoneDao.deletePage(
            offset = page * pageSize,
            limit = pageSize
        )
    }
}
