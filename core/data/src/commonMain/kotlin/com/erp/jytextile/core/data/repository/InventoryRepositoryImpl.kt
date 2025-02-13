package com.erp.jytextile.core.data.repository

import com.erp.jytextile.core.database.dao.InventoryDao
import com.erp.jytextile.core.database.model.FabricRollEntity
import com.erp.jytextile.core.database.model.SectionEntity
import com.erp.jytextile.core.database.model.SectionWithRollCountEntity
import com.erp.jytextile.core.database.model.toDomain
import com.erp.jytextile.core.database.model.toEntity
import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.core.domain.model.Section
import com.erp.jytextile.core.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import me.tatarka.inject.annotations.Inject

@Inject
class InventoryRepositoryImpl(
    private val inventoryDao: InventoryDao,
) : InventoryRepository {

    override suspend fun addSection(name: String) {
        inventoryDao.insertSection(SectionEntity(name = name))
    }

    override fun getSections(page: Int): Flow<List<Section>> {
        return inventoryDao.getSections(
            limit = PAGE_SIZE,
            offset = page * PAGE_SIZE,
        ).map { sections ->
            sections.map(SectionWithRollCountEntity::toDomain)
        }
    }

    override fun getSectionsCount(): Flow<Int> {
        return inventoryDao.getSectionsCount()
    }

    override fun getSectionPages(): Flow<Int> {
        return inventoryDao.getSectionsCount().map { count ->
            (count + PAGE_SIZE - 1) / PAGE_SIZE
        }
    }

    override fun getFabricRolls(
        sectionId: Long,
        page: Int,
        filterHasRemaining: Boolean
    ): Flow<List<FabricRoll>> {
        return inventoryDao.getFabricRolls(
            sectionId = sectionId,
            limit = PAGE_SIZE,
            offset = page * PAGE_SIZE,
            filterHasRemaining = filterHasRemaining
        ).map { rolls ->
            rolls.map(FabricRollEntity::toDomain)
        }
    }

    override suspend fun addFabricRoll(fabricRoll: FabricRoll) {
        inventoryDao.insertFabricRoll(fabricRoll.toEntity())
    }

    override suspend fun removeFabricRoll(rollId: Long) {
        inventoryDao.deleteFabricRoll(rollId)
    }

    override suspend fun releaseFabricRoll(
        rollId: Long,
        quantity: Double,
        destination: String,
        releaseDate: Instant
    ) {
        TODO("Not yet implemented")
    }
}

private const val PAGE_SIZE = 20
