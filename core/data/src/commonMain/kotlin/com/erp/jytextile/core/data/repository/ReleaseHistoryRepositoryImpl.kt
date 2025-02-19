package com.erp.jytextile.core.data.repository

import com.erp.jytextile.core.database.dao.InventoryDao
import com.erp.jytextile.core.database.model.ReleaseHistoryEntity
import com.erp.jytextile.core.database.model.toDomain
import com.erp.jytextile.core.domain.model.ReleaseHistory
import com.erp.jytextile.core.domain.repository.ReleaseHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class ReleaseHistoryRepositoryImpl(
    private val inventoryDao: InventoryDao,
): ReleaseHistoryRepository {

    override fun getReleaseHistories(rollId: Long, page: Int): Flow<List<ReleaseHistory>> {
        return inventoryDao.getReleaseHistory(
            rollId = rollId,
            limit = PAGE_SIZE,
            offset = page * PAGE_SIZE,
        ).map { histories ->
            histories.map(ReleaseHistoryEntity::toDomain)
        }
    }

    override fun getReleaseHistoriesPage(rollId: Long): Flow<Int> {
        return inventoryDao.getReleaseHistoryCount(rollId).map { count ->
            (count + PAGE_SIZE - 1) / PAGE_SIZE
        }
    }

    override suspend fun removeReleaseHistory(id: Long) {
        inventoryDao.deleteReleaseHistory(id)
    }
}

private const val PAGE_SIZE = 20
