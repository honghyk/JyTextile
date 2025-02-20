package com.erp.jytextile.core.data.repository

import com.erp.jytextile.core.database.dao.InventoryDao
import com.erp.jytextile.core.database.model.FabricRollEntity
import com.erp.jytextile.core.database.model.toDomain
import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.core.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class SearchRepositoryImpl(
    private val inventoryDao: InventoryDao
) : SearchRepository {

    override fun searchRoll(
        searchQuery: String,
        limit: Int,
        offset: Int
    ): Flow<List<FabricRoll>> {
        return inventoryDao.searchFabricRoll(
            query = searchQuery,
            limit = limit,
            offset = offset
        ).map { fabricRolls ->
            fabricRolls.map(FabricRollEntity::toDomain)
        }
    }
}
