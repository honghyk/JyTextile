package com.erp.jytextile.core.domain.repository

import com.erp.jytextile.core.domain.model.ReleaseHistory
import kotlinx.coroutines.flow.Flow

interface ReleaseHistoryRepository {

    fun getReleaseHistories(
        rollId: Long,
        page: Int,
    ): Flow<List<ReleaseHistory>>

    fun getReleaseHistoriesPage(rollId: Long): Flow<Int>

    suspend fun removeReleaseHistory(id: Long)
}
