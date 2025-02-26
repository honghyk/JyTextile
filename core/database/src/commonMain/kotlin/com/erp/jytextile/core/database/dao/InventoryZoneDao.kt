package com.erp.jytextile.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.erp.jytextile.core.database.model.ZoneEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryZoneDao : EntityDao<ZoneEntity> {

    @Query("SELECT * FROM zones ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun getZones(offset: Int, limit: Int): Flow<List<ZoneEntity>>

    @Query("SELECT * FROM zones WHERE name = :name LIMIT 1")
    suspend fun findByName(name: String): ZoneEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(entity: ZoneEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(entities: List<ZoneEntity>)

    @Upsert
    override suspend fun upsert(entity: ZoneEntity): Long

    @Upsert
    override suspend fun upsert(entities: List<ZoneEntity>)

    @Query("DELETE FROM zones WHERE id = :id")
    override suspend fun delete(id: Long)

    @Query("DELETE FROM zones WHERE id IN (:ids)")
    override suspend fun delete(ids: List<Long>)

    @Query(
        value = """
            DELETE FROM zones
            WHERE id IN (SELECT id FROM zones ORDER BY id ASC LIMIT :limit OFFSET :offset)
        """
    )
    suspend fun deletePage(offset: Int, limit: Int)

    @Query("DELETE FROM zones")
    override suspend fun deleteAll()
}
