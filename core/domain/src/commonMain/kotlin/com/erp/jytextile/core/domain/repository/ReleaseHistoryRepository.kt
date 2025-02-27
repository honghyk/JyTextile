package com.erp.jytextile.core.domain.repository

import com.erp.jytextile.core.domain.model.ReleaseHistory
import kotlinx.coroutines.flow.Flow

interface ReleaseHistoryRepository {

    fun getReleaseHistories(
        rollId: Long,
    ): Flow<List<ReleaseHistory>>

    suspend fun removeReleaseHistories(ids: List<Long>)
}
