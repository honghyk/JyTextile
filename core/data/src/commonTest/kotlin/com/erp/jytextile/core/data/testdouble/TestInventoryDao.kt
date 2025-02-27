package com.erp.jytextile.core.data.testdouble

import com.erp.jytextile.core.database.dao.InventoryDao
import com.erp.jytextile.core.database.model.FabricRollEntity
import com.erp.jytextile.core.database.model.FabricRollWithZoneEntity
import com.erp.jytextile.core.database.model.ReleaseHistoryEntity
import com.erp.jytextile.core.database.model.ZoneEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TestInventoryDao : InventoryDao {

    private val sectionEntitiesStateFlow = MutableStateFlow<List<ZoneEntity>>(emptyList())

    private val fabricRollEntitiesStateFlow = MutableStateFlow<List<FabricRollEntity>>(emptyList())

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

    override fun getFabricRolls(
        zoneId: Long,
        limit: Int,
        offset: Int,
        filterHasRemaining: Boolean
    ): Flow<List<FabricRollEntity>> {
        return fabricRollEntitiesStateFlow.map { rolls ->
            rolls
                .filter { it.zoneId == zoneId }
                .subList(
                    fromIndex = offset,
                    toIndex = (offset + limit).coerceAtMost(rolls.size)
                )
        }
    }

    override fun getFabricRollWithZone(rollId: Long): Flow<FabricRollWithZoneEntity?> {
        return combine(
            sectionEntitiesStateFlow,
            fabricRollEntitiesStateFlow,
            ::Pair
        ).map { (sections, rolls) ->
            rolls
                .filter { it.id == rollId }
                .map {
                    FabricRollWithZoneEntity(
                        fabricRoll = it,
                        zoneName = sections.find { section -> section.id == it.zoneId }!!.name
                    )
                }
                .firstOrNull()
        }
    }

    override suspend fun upsertFabricRoll(fabricRoll: FabricRollEntity): Long {
        fabricRollEntitiesStateFlow.update { oldValues ->
            (oldValues + fabricRoll)
                .distinctBy(FabricRollEntity::id)
                .sortedBy { it.id }
        }
        return fabricRoll.id
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

    override suspend fun getReleaseHistoryById(id: Long): ReleaseHistoryEntity? {
        TODO("Not yet implemented")
    }

    override fun getReleaseHistoryCount(rollId: Long): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReleaseHistory(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getFabricRollRemainingLength(rollId: Long): Double {
        TODO("Not yet implemented")
    }

    override fun getFabricRollsCount(zoneId: Long): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun getFabricRollSearchResultCount(query: String): Int {
        TODO("Not yet implemented")
    }

    override suspend fun updateFabricRoll(fabricRoll: FabricRollEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun searchFabricRoll(
        query: String,
        limit: Int,
        offset: Int
    ): List<FabricRollEntity> {
        TODO("Not yet implemented")
    }
}
