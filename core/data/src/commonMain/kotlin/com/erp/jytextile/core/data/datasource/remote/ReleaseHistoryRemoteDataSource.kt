package com.erp.jytextile.core.data.datasource.remote

import com.erp.jytextile.core.domain.model.ReleaseHistory

interface ReleaseHistoryRemoteDataSource {

    suspend fun getReleaseHistories(rollId: Long): List<ReleaseHistory>

    suspend fun deleteReleaseHistories(ids: List<Long>)
}
