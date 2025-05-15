package com.erp.trillion.core.database.datasource

import com.erp.trillion.core.data.datasource.local.ReleaseHistoryLocalDataSource
import com.erp.trillion.core.database.dao.ReleaseHistoryDao
import com.erp.trillion.core.database.model.ReleaseHistoryEntity
import com.erp.trillion.core.database.model.toDomain
import com.erp.trillion.core.database.model.toEntity
import com.erp.trillion.core.domain.model.ReleaseHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class ReleaseHistoryLocalDataSourceImpl(
    private val releaseHistoryDao: ReleaseHistoryDao
) : ReleaseHistoryLocalDataSource {

    override fun getReleaseHistories(rollId: Long): Flow<List<ReleaseHistory>> {
        return releaseHistoryDao.getReleaseHistories(rollId)
            .map { releaseHistories -> releaseHistories.map(ReleaseHistoryEntity::toDomain) }
    }

    override suspend fun insertReleaseHistory(releaseHistory: ReleaseHistory) {
        releaseHistoryDao.insertTransaction(releaseHistory.toEntity())
    }

    override suspend fun insertReleaseHistory(releaseHistories: List<ReleaseHistory>) {
        releaseHistories.forEach { insertReleaseHistory(it) }
    }

    override suspend fun deleteReleaseHistories(rollId: Long) {
        releaseHistoryDao.deleteAllTransaction(rollId)
    }

    override suspend fun deleteReleaseHistories(ids: List<Long>) {
        val rollId = ids
            .firstNotNullOfOrNull { releaseHistoryDao.getReleaseHistoryById(it) }
            ?.rollId
            ?: return

        releaseHistoryDao.deleteTransaction(rollId, ids)
    }
}
