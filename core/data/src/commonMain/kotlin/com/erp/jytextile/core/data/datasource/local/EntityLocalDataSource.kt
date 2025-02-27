package com.erp.jytextile.core.data.datasource.local

import com.erp.jytextile.core.domain.model.Entity

interface EntityLocalDataSource<E: Entity> {

    suspend fun upsert(entity: E): Long

    suspend fun upsert(entities: List<E>)

    suspend fun delete(id: Long)
}
