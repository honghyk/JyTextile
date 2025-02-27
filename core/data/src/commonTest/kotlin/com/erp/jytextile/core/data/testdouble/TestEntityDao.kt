package com.erp.jytextile.core.data.testdouble

import com.erp.jytextile.core.database.dao.EntityDao
import com.erp.jytextile.core.database.model.LocalEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

abstract class TestEntityDao<E : LocalEntity> : EntityDao<E> {

    protected abstract val ignoreOnConflict: Boolean
    protected val entities = MutableStateFlow<List<E>>(emptyList())

    override suspend fun insert(entity: E): Long {
        if (ignoreOnConflict && entities.value.any { it.id == entity.id }) {
            return -1
        }
        entities.update { oldValues ->
            (oldValues + entity)
                .distinctBy { it.id }
        }
        return entity.id
    }

    override suspend fun insert(entities: List<E>) {
        this.entities.update { oldValues ->
            (oldValues + entities)
                .distinctBy { it.id }
        }
    }

    override suspend fun upsert(entity: E): Long {
        entities.update { oldValues ->
            (oldValues + entity)
                .distinctBy { it.id }
        }
        return entity.id
    }

    override suspend fun upsert(entities: List<E>) {
        this.entities.update { oldValues ->
            (oldValues + entities)
                .distinctBy { it.id }
        }
    }

    override suspend fun delete(id: Long) {
        entities.map { entities ->
            entities.filterNot { it.id == id }
        }
    }

    override suspend fun delete(ids: List<Long>) {
        entities.update { oldValues ->
            oldValues.filterNot { it.id in ids }
        }
    }

    override suspend fun deleteAll() {
        entities.value = emptyList()
    }
}
