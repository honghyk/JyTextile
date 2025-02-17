package com.erp.jytextile.core.data.repository

import com.erp.jytextile.core.database.dao.InventoryDao
import com.erp.jytextile.core.database.model.FabricRollEntity
import com.erp.jytextile.core.database.model.ZoneEntity
import com.erp.jytextile.core.database.model.ZoneWithRollCountEntity
import com.erp.jytextile.core.database.model.toDomain
import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.core.domain.model.LengthUnit
import com.erp.jytextile.core.domain.model.Zone
import com.erp.jytextile.core.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import me.tatarka.inject.annotations.Inject

@Inject
class InventoryRepositoryImpl(
    private val inventoryDao: InventoryDao,
) : InventoryRepository {

    override suspend fun addZone(name: String) {
        inventoryDao.insertZone(ZoneEntity(name = name))
    }

    override fun getZones(page: Int): Flow<List<Zone>> {
        return inventoryDao.getZones(
            limit = PAGE_SIZE,
            offset = page * PAGE_SIZE,
        ).map { sections ->
            sections.map(ZoneWithRollCountEntity::toDomain)
        }
    }

    override fun getZonesCount(): Flow<Int> {
        return inventoryDao.getZonesCount()
    }

    override fun getZonePage(): Flow<Int> {
        return inventoryDao.getZonesCount().map { count ->
            (count + PAGE_SIZE - 1) / PAGE_SIZE
        }
    }

    override fun getFabricRolls(
        zoneId: Long,
        page: Int,
        filterHasRemaining: Boolean
    ): Flow<List<FabricRoll>> {
        return inventoryDao.getFabricRolls(
            zoneId = zoneId,
            limit = PAGE_SIZE,
            offset = page * PAGE_SIZE,
            filterHasRemaining = filterHasRemaining
        ).map { rolls ->
            rolls.map(FabricRollEntity::toDomain)
        }
    }

    override suspend fun addFabricRoll(
        zoneId: Long,
        itemNo: String,
        color: String,
        factory: String,
        quantity: Int,
        remark: String,
        lengthUnit: LengthUnit
    ) {
        val length = if (lengthUnit == LengthUnit.METER) quantity else yardToMeter(quantity)
        val roll = FabricRollEntity(
            zoneId = zoneId,
            code = itemNo,
            color = color,
            factory = factory,
            remainingLength = length,
            originalLength = length,
            remark = remark,
        )
        inventoryDao.insertFabricRoll(roll)
    }

    override suspend fun addFabricRoll(
        zoneName: String,
        itemNo: String,
        color: String,
        factory: String,
        quantity: Int,
        remark: String,
        lengthUnit: LengthUnit
    ) {
        val zone = inventoryDao.findZoneByName(zoneName)
        val zoneId = zone?.id ?: inventoryDao.insertZone(ZoneEntity(name = zoneName))

        addFabricRoll(
            zoneId = zoneId,
            itemNo = itemNo,
            color = color,
            factory = factory,
            quantity = quantity,
            remark = remark,
            lengthUnit = lengthUnit
        )
    }

    override suspend fun removeFabricRoll(rollId: Long) {
        inventoryDao.deleteFabricRoll(rollId)
    }

    override fun getFabricRollsPage(zoneId: Long): Flow<Int> {
        return inventoryDao.getFabricRollsCount(zoneId).map { count ->
            (count + PAGE_SIZE - 1) / PAGE_SIZE
        }
    }

    override fun getFabricRollsCount(zoneId: Long): Flow<Int> {
        return inventoryDao.getFabricRollsCount(zoneId)
    }

    override suspend fun releaseFabricRoll(
        rollId: Long,
        quantity: Double,
        destination: String,
        releaseDate: Instant
    ) {
        TODO("Not yet implemented")
    }

    private fun yardToMeter(yard: Int): Int {
        return (yard * 0.9144).toInt()
    }
}

private const val PAGE_SIZE = 20
