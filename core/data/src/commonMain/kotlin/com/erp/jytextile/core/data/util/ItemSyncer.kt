package com.erp.jytextile.core.data.util

import co.touchlab.kermit.Logger
import com.erp.jytextile.core.data.datasource.local.EntityLocalDataSource
import com.erp.jytextile.core.domain.model.Entity

class ItemSyncer<T : Entity>(
    private val upsertEntity: suspend (T) -> Long,
    private val deleteEntity: suspend (T) -> Unit,
) {
    private val logger by lazy { Logger.withTag("ItemSyncer") }

    suspend fun sync(
        currentValues: Collection<T>,
        networkValues: Collection<T>,
    ): ItemSyncerResult<T> {
        val currentLocalEntities = ArrayList(currentValues)

        val removed = ArrayList<T>()
        val added = ArrayList<T>()
        val updated = ArrayList<T>()

        for (networkEntity in networkValues) {
            logger.v { "Syncing item from network: $networkEntity" }

            val remoteId = networkEntity.id
            val localEntityForId = currentLocalEntities.find { it.id == remoteId }

            if (localEntityForId != null) {
                if (localEntityForId != networkEntity) {
                    upsertEntity(networkEntity)
                    logger.v { "Updated entity: ${networkEntity.id}" }
                }
                currentLocalEntities.remove(localEntityForId)
                updated += networkEntity
            } else {
                added += networkEntity
            }
        }

        currentLocalEntities.forEach {
            deleteEntity(it)
            logger.v { "Deleted entity: ${it.id}" }
            removed += it
        }

        added.forEach {
            upsertEntity(it)
            logger.v { "Added entity: ${it.id}" }
        }

        return ItemSyncerResult(added, removed, updated)
    }
}

data class ItemSyncerResult<ET : Entity>(
    val added: List<ET> = emptyList(),
    val deleted: List<ET> = emptyList(),
    val updated: List<ET> = emptyList(),
)

fun <T : Entity> syncerForEntity(
    dataSource: EntityLocalDataSource<T>,
) = ItemSyncer(
    upsertEntity = dataSource::upsert,
    deleteEntity = dataSource::delete,
)
