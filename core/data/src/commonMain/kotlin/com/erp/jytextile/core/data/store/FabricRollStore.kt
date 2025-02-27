package com.erp.jytextile.core.data.store

import com.erp.jytextile.core.data.datasource.local.FabricRollLocalDataSource
import com.erp.jytextile.core.data.datasource.remote.FabricRollRemoteDataSource
import com.erp.jytextile.core.domain.model.FabricRoll
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.Store

@Inject
class FabricRollStore(
    private val localDataSource: FabricRollLocalDataSource,
    private val remoteDataSource: FabricRollRemoteDataSource,
) : Store<Long, FabricRoll> by storeBuilder(
    fetcher = Fetcher.of { rollId ->
        remoteDataSource.getFabricRoll(rollId)
    },
    sourceOfTruth = SourceOfTruth.of<Long, FabricRoll, FabricRoll>(
        reader = { rollId ->
            localDataSource.getFabricRoll(rollId)
        },
        writer = { _, fabricRoll -> localDataSource.upsert(fabricRoll) },
    )
).build()
