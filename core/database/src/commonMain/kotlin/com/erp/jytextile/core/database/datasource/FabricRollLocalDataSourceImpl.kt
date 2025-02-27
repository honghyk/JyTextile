package com.erp.jytextile.core.database.datasource

import com.erp.jytextile.core.database.dao.RollInventoryDao
import com.erp.jytextile.core.database.model.FabricRollEntity
import com.erp.jytextile.core.database.model.toDomain
import com.erp.jytextile.core.database.model.toEntity
import com.erp.jytextile.core.domain.model.FabricRoll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

interface FabricRollLocalDataSource {

    fun getFabricRolls(zoneId: Long, page: Int, pageSize: Int): Flow<List<FabricRoll>>

    suspend fun upsert(fabricRolls: List<FabricRoll>)

    suspend fun deleteByZoneId(zoneId: Long)
}

@Inject
class FabricRollLocalDataSourceImpl(
    private val rollInventoryDao: RollInventoryDao
) : FabricRollLocalDataSource {

    override fun getFabricRolls(zoneId: Long, page: Int, pageSize: Int): Flow<List<FabricRoll>> {
        return rollInventoryDao
            .getFabricRolls(
                zoneId = zoneId,
                offset = page * pageSize,
                limit = pageSize,
                filterHasRemaining = true
            )
            .map { rolls -> rolls.map(FabricRollEntity::toDomain) }
    }

    override suspend fun upsert(fabricRolls: List<FabricRoll>) {
        rollInventoryDao.upsert(fabricRolls.map { it.toEntity() })
    }

    override suspend fun deleteByZoneId(zoneId: Long) {
        rollInventoryDao.deleteByZoneId(zoneId)
    }
}
