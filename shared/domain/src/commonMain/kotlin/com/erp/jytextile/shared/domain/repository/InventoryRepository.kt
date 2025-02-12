package com.erp.jytextile.shared.domain.repository

import com.erp.jytextile.shared.domain.model.FabricRoll
import com.erp.jytextile.shared.domain.model.Section
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

interface InventoryRepository {

    fun getSections(): Flow<List<Section>>

    fun getFabricRolls(
        sectionId: Long,
        page: Int,
        filterHasRemaining: Boolean
    ): Flow<List<FabricRoll>>

    fun addFabricRoll(fabricRoll: FabricRoll)

    fun removeFabricRoll(rollId: Long)

    fun releaseFabricRoll(
        rollId: Long,
        quantity: Double,
        destination: String,
        releaseDate: Instant = Clock.System.now(),
    )
}
