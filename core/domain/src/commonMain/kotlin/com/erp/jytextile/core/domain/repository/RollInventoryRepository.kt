package com.erp.jytextile.core.domain.repository

import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.core.domain.model.FabricRollInsertion
import com.erp.jytextile.core.domain.model.LengthUnit
import kotlinx.coroutines.flow.Flow

interface RollInventoryRepository {

    fun getFabricRolls(
        zoneId: Long,
        page: Int,
        pageSize: Int,
    ): Flow<List<FabricRoll>>

    fun getRoll(rollId: Long): Flow<FabricRoll>

    suspend fun upsertFabricRoll(
        zoneId: Long,
        rollInsertion: FabricRollInsertion,
    )

    suspend fun upsertFabricRoll(
        zoneName: String,
        rollInsertion: FabricRollInsertion,
    )

    suspend fun removeFabricRoll(rollId: Long)

    suspend fun releaseFabricRoll(
        rollId: Long,
        quantity: Double,
        lengthUnit: LengthUnit,
        buyer: String,
        releaseDate: String,
        remark: String,
    )
}
