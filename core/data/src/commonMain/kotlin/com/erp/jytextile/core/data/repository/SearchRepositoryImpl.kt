package com.erp.jytextile.core.data.repository

import com.erp.jytextile.core.database.dao.InventoryDao
import com.erp.jytextile.core.database.model.FabricRollEntity
import com.erp.jytextile.core.database.model.toDomain
import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.core.domain.repository.SearchRepository
import me.tatarka.inject.annotations.Inject

@Inject
class SearchRepositoryImpl(
    private val inventoryDao: InventoryDao
) : SearchRepository {

    override suspend fun searchRoll(
        searchQuery: String,
        limit: Int,
        offset: Int
    ): List<FabricRoll> {
        if (searchQuery.isBlank()) return emptyList()

        return inventoryDao
            .searchFabricRoll(
                query = searchQuery,
                limit = limit,
                offset = offset
            )
            .map(FabricRollEntity::toDomain)
    }

    override suspend fun getSearchResultPageCount(searchQuery: String, pageSize: Int): Int {
        if (searchQuery.isBlank()) return 0

        val count = inventoryDao.getFabricRollSearchResultCount(searchQuery)
        return (count + pageSize - 1) / pageSize
    }
}
