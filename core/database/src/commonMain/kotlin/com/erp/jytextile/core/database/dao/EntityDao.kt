package com.erp.jytextile.core.database.dao

import com.erp.jytextile.core.database.model.LocalEntity

interface EntityDao<E : LocalEntity> {

    suspend fun insert(entity: E): Long

    suspend fun insert(entities: List<E>)

    suspend fun upsert(entity: E): Long

    suspend fun upsert(entities: List<E>)

    suspend fun delete(id: Long)

    suspend fun delete(ids: List<Long>)

    suspend fun deleteAll()
}
