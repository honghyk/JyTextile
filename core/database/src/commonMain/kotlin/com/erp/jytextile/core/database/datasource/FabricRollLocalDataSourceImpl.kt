package com.erp.jytextile.core.database.datasource

import com.erp.jytextile.core.database.dao.RollInventoryDao
import com.erp.jytextile.core.database.model.FabricRollEntity
import com.erp.jytextile.core.database.model.FabricRollWithZoneEntity
import com.erp.jytextile.core.database.model.toDomain
import com.erp.jytextile.core.database.model.toEntity
import com.erp.jytextile.core.domain.model.FabricRoll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

interface FabricRollLocalDataSource {

    fun getFabricRolls(zoneId: Long, page: Int, pageSize: Int): Flow<List<FabricRoll>>

    fun getFabricRoll(rollId: Long): Flow<FabricRoll>

    suspend fun upsert(fabricRoll: FabricRoll)

    suspend fun upsert(fabricRolls: List<FabricRoll>)

    suspend fun delete(rollId: Long)

    suspend fun deleteByZoneId(zoneId: Long)

    suspend fun search(query: String): List<FabricRoll>
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
                filterHasRemaining = false
            )
            .map { rolls -> rolls.map(FabricRollEntity::toDomain) }
    }

    override fun getFabricRoll(rollId: Long): Flow<FabricRoll> {
        return rollInventoryDao.getFabricRoll(rollId).map(FabricRollWithZoneEntity::toDomain)
    }

    override suspend fun upsert(fabricRoll: FabricRoll) {
        rollInventoryDao.upsert(fabricRoll.toEntity())
    }

    override suspend fun upsert(fabricRolls: List<FabricRoll>) {
        rollInventoryDao.upsert(fabricRolls.map(FabricRoll::toEntity))
    }

    override suspend fun delete(rollId: Long) {
        rollInventoryDao.delete(rollId)
    }

    override suspend fun deleteByZoneId(zoneId: Long) {
        rollInventoryDao.deleteByZoneId(zoneId)
    }

    override fun search(query: String): Flow<List<FabricRoll>> {
        return rollInventoryDao.search(query).map { rolls -> rolls.map(FabricRollEntity::toDomain) }
    }
}
