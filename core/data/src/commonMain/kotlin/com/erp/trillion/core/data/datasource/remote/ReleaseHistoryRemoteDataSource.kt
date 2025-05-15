package com.erp.trillion.core.data.datasource.remote

import com.erp.trillion.core.domain.model.ReleaseHistory

interface ReleaseHistoryRemoteDataSource {

    suspend fun getReleaseHistories(rollId: Long): List<ReleaseHistory>

    suspend fun deleteReleaseHistories(ids: List<Long>)
}
