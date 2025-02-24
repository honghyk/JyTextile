package com.erp.jytextile.core.data.repository

import com.erp.jytextile.core.database.dao.InventoryDao
import com.erp.jytextile.core.database.model.FabricRollEntity
import com.erp.jytextile.core.database.model.FabricRollWithZoneEntity
import com.erp.jytextile.core.database.model.ReleaseHistoryEntity
import com.erp.jytextile.core.database.model.ZoneEntity
import com.erp.jytextile.core.database.model.toDomain
import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.core.domain.model.FabricRollInsertion
import com.erp.jytextile.core.domain.model.LengthUnit
import com.erp.jytextile.core.domain.repository.RollInventoryRepository
import com.erp.jytextile.kotlin.utils.yardToMeter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import me.tatarka.inject.annotations.Inject

@Inject
class RollInventoryRepositoryImpl(
    private val inventoryDao: InventoryDao,
): RollInventoryRepository {

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

    override fun getRoll(rollId: Long): Flow<FabricRoll> {
        return inventoryDao.getFabricRollWithZone(rollId)
            .filterNotNull()
            .map(FabricRollWithZoneEntity::toDomain)
    }

    override fun getFabricRollsPage(zoneId: Long): Flow<Int> {
        return inventoryDao.getFabricRollsCount(zoneId).map { count ->
            (count + PAGE_SIZE - 1) / PAGE_SIZE
        }
    }

    override suspend fun addFabricRoll(
        zoneId: Long,
        rollInsertion: FabricRollInsertion,
    ) {
        val roll = FabricRollEntity(
            zoneId = zoneId,
            id = rollInsertion.id,
            itemNo = rollInsertion.itemNo,
            orderNo = rollInsertion.orderNo,
            color = rollInsertion.color,
            factory = rollInsertion.factory,
            finish = rollInsertion.finish,
            remainingLength = rollInsertion.quantity,
            originalLength = rollInsertion.quantity,
            remark = rollInsertion.remark,
        )
        inventoryDao.insertFabricRoll(roll)
    }

    override suspend fun addFabricRoll(
        zoneName: String,
        rollInsertion: FabricRollInsertion,
    ) {
        val zone = inventoryDao.findZoneByName(zoneName)
        val zoneId = zone?.id ?: inventoryDao.insertZone(ZoneEntity(name = zoneName))

        addFabricRoll(
            zoneId = zoneId,
            rollInsertion = rollInsertion,
        )
    }

    override fun getFabricRollsCount(zoneId: Long): Flow<Int> {
        return inventoryDao.getFabricRollsCount(zoneId)
    }

    override suspend fun removeFabricRoll(rollId: Long) {
        inventoryDao.deleteFabricRoll(rollId)
    }

    override suspend fun releaseFabricRoll(
        rollId: Long,
        quantity: Double,
        lengthUnit: LengthUnit,
        buyer: String,
        releaseDate: String,
        remark: String,
    ) {
        val roll = getRoll(rollId).firstOrNull() ?: return
        val length = if (lengthUnit == LengthUnit.METER) quantity else quantity.yardToMeter()
        if (length > roll.remainingQuantity) {
            throw IllegalStateException("release more than remaining")
        }
        val date = LocalDate
            .parse(releaseDate, LocalDate.Formats.ISO_BASIC)
            .atStartOfDayIn(TimeZone.UTC)

        inventoryDao.releaseFabricRollTransaction(
            releaseHistory = ReleaseHistoryEntity(
                rollId = rollId,
                quantity = quantity,
                destination = buyer,
                releaseDate = date,
                remark = remark,
            ),
            rollId = rollId,
            newRemainingLength = roll.remainingQuantity - length
        )
    }
}

private const val PAGE_SIZE = 20
