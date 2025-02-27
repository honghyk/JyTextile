package com.erp.jytextile.core.data.repository

import com.erp.jytextile.core.data.datasource.remote.FabricRollRemoteDataSource
import com.erp.jytextile.core.data.store.FabricRollStore
import com.erp.jytextile.core.data.store.FabricRollsStore
import com.erp.jytextile.core.data.store.PagingKey
import com.erp.jytextile.core.database.datasource.FabricRollLocalDataSource
import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.core.domain.model.LengthUnit
import com.erp.jytextile.core.domain.repository.RollInventoryRepository
import com.erp.jytextile.kotlin.utils.yardToMeter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import org.mobilenativefoundation.store.store5.impl.extensions.get

@Inject
class RollInventoryRepositoryImpl(
    private val fabricRollLocalDataSource: FabricRollLocalDataSource,
    private val fabricRollRemoteDataSource: FabricRollRemoteDataSource,
    private val fabricRollStore: FabricRollStore,
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
            .distinctUntilChanged()
    }

    override fun getRoll(rollId: Long): Flow<FabricRoll> {
        return fabricRollStore
            .stream(StoreReadRequest.cached(rollId, true))
            .filter { it is StoreReadResponse.Data }
            .map { it.requireData() }
            .distinctUntilChanged()
    }

    override suspend fun upsertFabricRoll(fabricRoll: FabricRoll) {
        fabricRollRemoteDataSource.upsert(fabricRoll)
        fabricRollLocalDataSource.upsert(fabricRoll)
    }

    override suspend fun removeFabricRoll(rollId: Long) {
        fabricRollRemoteDataSource.delete(rollId)
        fabricRollLocalDataSource.delete(rollId)
    }

    override suspend fun releaseFabricRoll(
        rollId: Long,
        quantity: Double,
        lengthUnit: LengthUnit,
        buyer: String,
        releaseDate: String,
        remark: String,
    ) {
        val roll = fabricRollStore.get(rollId)
        val length = if (lengthUnit == LengthUnit.METER) quantity else quantity.yardToMeter()
        if (length > roll.remainingQuantity) {
            throw IllegalStateException("release more than remaining")
        }
        val date = LocalDate
            .parse(releaseDate, LocalDate.Formats.ISO_BASIC)
            .atStartOfDayIn(TimeZone.UTC)

        fabricRollRemoteDataSource.releaseFabricRoll(
            rollId = rollId,
            quantity = length,
            buyer = buyer,
            releaseAt = date,
            remark = remark,
        )
        fabricRollLocalDataSource.upsert(
            roll.copy(
                remainingQuantity = roll.originalQuantity - length
            )
        )
    }
}
