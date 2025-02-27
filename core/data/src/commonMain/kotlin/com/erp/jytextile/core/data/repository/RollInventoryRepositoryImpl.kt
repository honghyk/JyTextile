package com.erp.jytextile.core.data.repository

import com.erp.jytextile.core.data.store.FabricRollsStore
import com.erp.jytextile.core.data.store.PagingKey
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
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse

@Inject
class RollInventoryRepositoryImpl(
    private val inventoryDao: InventoryDao,
    private val fabricRollsStore: FabricRollsStore,
) : RollInventoryRepository {

    override fun getFabricRolls(
        zoneId: Long,
        page: Int,
        pageSize: Int,
    ): Flow<List<FabricRoll>> {
        return fabricRollsStore
            .stream(
                StoreReadRequest.cached(
                    key = Pair(zoneId, PagingKey(page, pageSize)),
                    refresh = true
                )
            )
            .filter { it is StoreReadResponse.Data }
            .map { it.requireData() }
    }

    override fun getRoll(rollId: Long): Flow<FabricRoll> {
        return inventoryDao.getFabricRollWithZone(rollId)
            .filterNotNull()
            .map(FabricRollWithZoneEntity::toDomain)
    }

    override suspend fun upsertFabricRoll(
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
        inventoryDao.upsertFabricRoll(roll)
    }

    override suspend fun upsertFabricRoll(
        zoneName: String,
        rollInsertion: FabricRollInsertion,
    ) {
        val zone = inventoryDao.findZoneByName(zoneName)
        val zoneId = zone?.id ?: inventoryDao.insertZone(ZoneEntity(name = zoneName))

        upsertFabricRoll(
            zoneId = zoneId,
            rollInsertion = rollInsertion,
        )
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
        )
    }
}
