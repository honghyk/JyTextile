package com.erp.jytextile.core.domain.repository

import com.erp.jytextile.core.domain.model.FabricRoll
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun searchRoll(searchQuery: String): Flow<List<FabricRoll>>
}
