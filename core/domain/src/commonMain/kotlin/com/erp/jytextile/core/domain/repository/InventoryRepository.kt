package com.erp.jytextile.core.domain.repository

import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.core.domain.model.Section
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

interface InventoryRepository {

    suspend fun addSection(name: String)

    fun getSections(
        page: Int,
    ): Flow<List<Section>>

    fun getSectionsCount(): Flow<Int>

    fun getSectionPages(): Flow<Int>

    fun getFabricRolls(
        sectionId: Long,
        page: Int,
        filterHasRemaining: Boolean
    ): Flow<List<FabricRoll>>

    suspend fun addFabricRoll(fabricRoll: FabricRoll)

    suspend fun removeFabricRoll(rollId: Long)

    suspend fun releaseFabricRoll(
        rollId: Long,
        quantity: Double,
        destination: String,
        releaseDate: Instant = Clock.System.now(),
    )
}
