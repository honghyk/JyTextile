package com.erp.jytextile.core.data.repository

import com.erp.jytextile.core.data.datasource.local.FabricRollLocalDataSource
import com.erp.jytextile.core.data.datasource.remote.FabricRollRemoteDataSource
import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.core.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import me.tatarka.inject.annotations.Inject

@Inject
class SearchRepositoryImpl(
    private val fabricRollLocalDataSource: FabricRollLocalDataSource,
    private val fabricRollRemoteDataSource: FabricRollRemoteDataSource,
) : SearchRepository {

    private val cache = mutableMapOf<String, Instant>()

    override fun searchRoll(
        searchQuery: String,
        limit: Int,
        offset: Int
    ): Flow<List<FabricRoll>> {
        if (searchQuery.isBlank()) return flowOf(emptyList())

        return fabricRollLocalDataSource.search(searchQuery)
            .onStart {
                val lastUpdated = cache[searchQuery]
                if (lastUpdated == null || (Clock.System.now() - lastUpdated).inWholeMinutes > 30) {
                    runCatching {
                        fabricRollRemoteDataSource.search(searchQuery).also {
                            fabricRollLocalDataSource.upsert(it)
                        }
                        cache[searchQuery] = Clock.System.now()
                    }
                }
            }
    }
}
