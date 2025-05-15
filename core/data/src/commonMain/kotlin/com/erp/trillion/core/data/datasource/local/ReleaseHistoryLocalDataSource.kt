package com.erp.trillion.core.data.datasource.local

import com.erp.trillion.core.domain.model.ReleaseHistory
import kotlinx.coroutines.flow.Flow

interface ReleaseHistoryLocalDataSource {
    fun getReleaseHistories(rollId: Long): Flow<List<ReleaseHistory>>

    suspend fun insertReleaseHistory(releaseHistory: ReleaseHistory)

    suspend fun insertReleaseHistory(releaseHistories: List<ReleaseHistory>)

    suspend fun deleteReleaseHistories(rollId: Long)

    suspend fun deleteReleaseHistories(ids: List<Long>)
}
