package com.erp.trillion.core.domain.repository

import com.erp.trillion.core.domain.model.FabricRoll
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun searchRoll(searchQuery: String): Flow<List<FabricRoll>>
}
