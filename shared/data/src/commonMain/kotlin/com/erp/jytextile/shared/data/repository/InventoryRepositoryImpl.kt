package com.erp.jytextile.shared.data.repository

import com.erp.jytextile.shared.domain.model.FabricRoll
import com.erp.jytextile.shared.domain.model.Section
import com.erp.jytextile.shared.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

internal class InventoryRepositoryImpl : InventoryRepository {

    override fun getSections(): Flow<List<Section>> {
        TODO("Not yet implemented")
    }

    override fun getFabricRolls(
        sectionId: Long,
        page: Int,
        filterHasRemaining: Boolean
    ): Flow<List<FabricRoll>> {
        TODO("Not yet implemented")
    }

    override fun addFabricRoll(fabricRoll: FabricRoll) {
        TODO("Not yet implemented")
    }

    override fun removeFabricRoll(rollId: Long) {
        TODO("Not yet implemented")
    }

    override fun releaseFabricRoll(
        rollId: Long,
        quantity: Double,
        destination: String,
        releaseDate: Instant
    ) {
        TODO("Not yet implemented")
    }
}
