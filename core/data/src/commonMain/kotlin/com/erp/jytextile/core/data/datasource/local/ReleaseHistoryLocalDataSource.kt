package com.erp.jytextile.core.data.datasource.local

import com.erp.jytextile.core.domain.model.ReleaseHistory
import kotlinx.coroutines.flow.Flow

interface ReleaseHistoryLocalDataSource {
    fun getReleaseHistories(rollId: Long): Flow<List<ReleaseHistory>>

    suspend fun insertReleaseHistory(releaseHistory: ReleaseHistory)

    suspend fun insertReleaseHistory(releaseHistories: List<ReleaseHistory>)

    suspend fun deleteReleaseHistories(rollId: Long)

    suspend fun deleteReleaseHistories(ids: List<Long>)
}
