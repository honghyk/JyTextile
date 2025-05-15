package com.erp.trillion.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.erp.trillion.core.database.model.FabricRollEntity
import com.erp.trillion.core.database.model.FabricRollWithZoneEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RollInventoryDao : EntityDao<FabricRollEntity> {

    @Query(
        value = """
            SELECT * FROM fabric_rolls
            WHERE zone_id = :zoneId
            AND (:filterHasRemaining = 0 OR remaining_length > 0)
            ORDER BY id ASC
            LIMIT :limit 
            OFFSET :offset
            """
    )
    fun getFabricRolls(
        zoneId: Long,
        limit: Int,
        offset: Int,
        filterHasRemaining: Boolean,
    ): Flow<List<FabricRollEntity>>

    @Query(
        """
        SELECT fabric_rolls.*, zones.name AS zone_name
        FROM fabric_rolls
        INNER JOIN zones ON fabric_rolls.zone_id = zones.id
        WHERE fabric_rolls.id = :rollId
        LIMIT 1
        """
    )
    fun getFabricRoll(rollId: Long): Flow<FabricRollWithZoneEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(entity: FabricRollEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun insert(entities: List<FabricRollEntity>)

    @Upsert
    override suspend fun upsert(entity: FabricRollEntity): Long

    @Upsert
    override suspend fun upsert(entities: List<FabricRollEntity>)

    @Query("DELETE FROM fabric_rolls WHERE id = :id")
    override suspend fun delete(id: Long)

    @Query("DELETE FROM fabric_rolls WHERE id IN (:ids)")
    override suspend fun delete(ids: List<Long>)

    @Query("DELETE FROM fabric_rolls WHERE zone_id = :zoneId")
    suspend fun deleteByZoneId(zoneId: Long)

    @Query("DELETE FROM fabric_rolls")
    override suspend fun deleteAll()

    @Query(
        value = """
            SELECT * FROM fabric_rolls 
            WHERE (CAST(id AS TEXT) LIKE :query AND :query GLOB '[0-9]*') 
            OR itemNo LIKE '%' || :query || '%' 
            OR orderNo LIKE '%' || :query || '%'
            ORDER BY id ASC
            """
    )
    fun search(query: String): Flow<List<FabricRollEntity>>
}
