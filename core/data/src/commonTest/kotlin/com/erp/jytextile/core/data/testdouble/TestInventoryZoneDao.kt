package com.erp.jytextile.core.data.testdouble

import com.erp.jytextile.core.database.dao.InventoryZoneDao
import com.erp.jytextile.core.database.model.ZoneEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TestInventoryZoneDao : TestEntityDao<ZoneEntity>(), InventoryZoneDao {

    override val ignoreOnConflict: Boolean = false

    override fun getZones(offset: Int, limit: Int): Flow<List<ZoneEntity>> {
        return entities
            .map { entities ->
                entities.subList(
                    fromIndex = offset,
                    toIndex = (offset + limit).coerceAtMost(entities.size)
                )
            }
    }

    override suspend fun findByName(name: String): ZoneEntity? {
        return entities.value.find { it.name == name }
    }

    override suspend fun deletePage(offset: Int, limit: Int) {
        entities.update { oldValues ->
            oldValues.subList(0, offset) + oldValues.subList(offset + limit, oldValues.size)
        }
    }
}
