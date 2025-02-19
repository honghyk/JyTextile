package com.erp.jytextile.core.data.repository

import com.erp.jytextile.core.database.dao.InventoryDao
import com.erp.jytextile.core.database.model.ZoneEntity
import com.erp.jytextile.core.database.model.ZoneWithRollCountEntity
import com.erp.jytextile.core.database.model.toDomain
import com.erp.jytextile.core.domain.model.Zone
import com.erp.jytextile.core.domain.repository.ZoneInventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class ZoneInventoryRepositoryImpl(
    private val inventoryDao: InventoryDao,
) : ZoneInventoryRepository {

    override suspend fun addZone(name: String) {
        inventoryDao.insertZone(ZoneEntity(name = name))
    }

    override fun getZones(
        page: Int,
        pageSize: Int,
    ): Flow<List<Zone>> {
        return inventoryDao.getZones(
            limit = pageSize,
            offset = page * pageSize,
        ).map { sections ->
            sections.map(ZoneWithRollCountEntity::toDomain)
        }
    }

    override fun getZonesCount(): Flow<Int> {
        return inventoryDao.getZonesCount()
    }

    override fun getZonePage(pageSize: Int): Flow<Int> {
        return inventoryDao.getZonesCount().map { count ->
            (count + pageSize - 1) / pageSize
        }
    }
}
