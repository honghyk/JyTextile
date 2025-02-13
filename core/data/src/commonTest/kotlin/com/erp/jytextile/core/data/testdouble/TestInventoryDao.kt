package com.erp.jytextile.core.data.testdouble

import com.erp.jytextile.core.database.dao.InventoryDao
import com.erp.jytextile.core.database.model.FabricRollEntity
import com.erp.jytextile.core.database.model.ReleaseHistoryEntity
import com.erp.jytextile.core.database.model.SectionEntity
import com.erp.jytextile.core.database.model.SectionWithRollCountEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TestInventoryDao : InventoryDao {

    private val sectionEntitiesStateFlow = MutableStateFlow<List<SectionEntity>>(emptyList())

    private val fabricRollEntitiesStateFlow = MutableStateFlow<List<FabricRollEntity>>(emptyList())

    override fun getSections(limit: Int, offset: Int): Flow<List<SectionWithRollCountEntity>> {
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

    override suspend fun insertSection(section: SectionEntity): Long {
        sectionEntitiesStateFlow.update { oldValues ->
            (oldValues + section)
                .distinctBy(SectionEntity::id)
                .sortedBy { it.name }
        }
        return section.id
    }

    override suspend fun deleteSection(sectionId: Long) {
        sectionEntitiesStateFlow.update { entities ->
            entities.filterNot { it.id == sectionId }
        }
    }

    override fun getSectionsCount(): Flow<Int> {
        return sectionEntitiesStateFlow.map { it.size }
    }

    override fun getFabricRolls(
        sectionId: Long,
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

    override suspend fun updateFabricRollRemainingLength(rollId: Long, remaining: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun insertReleaseHistory(releaseHistory: ReleaseHistoryEntity): Long {
        TODO("Not yet implemented")
    }

    override fun getReleaseHistory(rollId: Long): Flow<List<ReleaseHistoryEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReleaseHistory(releaseHistoryId: Long) {
        TODO("Not yet implemented")
    }
}

private fun SectionEntity.toSectionWithRollCountEntity(
    fabricRolls: List<FabricRollEntity>
) = SectionWithRollCountEntity(
    section = this,
    rollCount = fabricRolls
        .filter { it.sectionId == id }
        .size
)
