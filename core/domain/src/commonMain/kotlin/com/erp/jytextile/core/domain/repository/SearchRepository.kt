package com.erp.jytextile.core.domain.repository

import com.erp.jytextile.core.domain.model.FabricRoll

interface SearchRepository {

    suspend fun searchRoll(
        searchQuery: String,
        limit: Int,
        offset: Int
    ): List<FabricRoll>

    suspend fun getSearchResultPageCount(searchQuery: String, pageSize: Int): Int
}
