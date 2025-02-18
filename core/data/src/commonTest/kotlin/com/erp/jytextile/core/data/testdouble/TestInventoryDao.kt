package com.erp.jytextile.core.data.testdouble

import com.erp.jytextile.core.database.dao.InventoryDao
import com.erp.jytextile.core.database.model.FabricRollEntity
import com.erp.jytextile.core.database.model.FabricRollWithZoneEntity
import com.erp.jytextile.core.database.model.ReleaseHistoryEntity
import com.erp.jytextile.core.database.model.ZoneEntity
import com.erp.jytextile.core.database.model.ZoneWithRollCountEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TestInventoryDao : InventoryDao {

    private val sectionEntitiesStateFlow = MutableStateFlow<List<ZoneEntity>>(emptyList())

    private val fabricRollEntitiesStateFlow = MutableStateFlow<List<FabricRollEntity>>(emptyList())

    override fun getZones(limit: Int, offset: Int): Flow<List<ZoneWithRollCountEntity>> {
        return combine(
            sectionEntitiesStateFlow,
            fabricRollEntitiesStateFlow,
            ::Pair
        ).map { (sections, rolls) ->
            sections.subList(
                fromIndex = offset,
                toIndex = (offset + limit).coerceAtMost(sections.size)
            ).map {
                it.toSectionWithRollCountEntity(rolls)
            }
        }
    }

    override suspend fun findZoneByName(name: String): ZoneEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun insertZone(section: ZoneEntity): Long {
        sectionEntitiesStateFlow.update { oldValues ->
            (oldValues + section)
                .distinctBy(ZoneEntity::id)
                .sortedBy { it.name }
        }
        return section.id
    }

    override suspend fun deleteZone(sectionId: Long) {
        sectionEntitiesStateFlow.update { entities ->
            entities.filterNot { it.id == sectionId }
        }
    }

    override fun getZonesCount(): Flow<Int> {
        return sectionEntitiesStateFlow.map { it.size }
    }

    override fun getFabricRolls(
        zoneId: Long,
        limit: Int,
        offset: Int,
        filterHasRemaining: Boolean
    ): Flow<List<FabricRollEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertFabricRoll(fabricRoll: FabricRollEntity): Long {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFabricRoll(rollId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun updateFabricRollRemainingLength(rollId: Long, remaining: Double) {
        TODO("Not yet implemented")
    }

    override suspend fun insertReleaseHistory(releaseHistory: ReleaseHistoryEntity): Long {
        TODO("Not yet implemented")
    }

    override fun getReleaseHistory(
        rollId: Long,
        limit: Int,
        offset: Int
    ): Flow<List<ReleaseHistoryEntity>> {
        TODO("Not yet implemented")
    }

    override fun getReleaseHistoryCount(rollId: Long): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReleaseHistory(releaseHistoryId: Long) {
        TODO("Not yet implemented")
    }

    override fun getFabricRollWithZone(rollId: Long): Flow<FabricRollWithZoneEntity?> {
        TODO("Not yet implemented")
    }

    override fun getFabricRollsCount(zoneId: Long): Flow<Int> {
        TODO("Not yet implemented")
    }
}

private fun ZoneEntity.toSectionWithRollCountEntity(
    fabricRolls: List<FabricRollEntity>
) = ZoneWithRollCountEntity(
    zone = this,
    rollCount = fabricRolls
        .filter { it.zoneId == id }
        .size
)
