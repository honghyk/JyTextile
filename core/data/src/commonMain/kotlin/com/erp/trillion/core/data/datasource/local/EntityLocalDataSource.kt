package com.erp.trillion.core.data.datasource.local

import com.erp.trillion.core.domain.model.Entity

interface EntityLocalDataSource<E: Entity> {

    suspend fun upsert(entity: E): Long

    suspend fun upsert(entities: List<E>)

    suspend fun delete(id: Long)
}
