package com.erp.jytextile.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.erp.jytextile.core.database.model.FabricRollEntity
import com.erp.jytextile.core.database.model.FabricRollWithZoneEntity
import com.erp.jytextile.core.database.model.ReleaseHistoryEntity
import com.erp.jytextile.core.database.model.ZoneEntity
import com.erp.jytextile.core.database.model.ZoneWithRollCountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {

    @Query(
        value = """
                SELECT zones.*, COUNT(fabric_rolls.id) AS rollCount
                FROM zones
                LEFT JOIN fabric_rolls ON zones.id = fabric_rolls.zone_id
                GROUP BY zones.id
                ORDER BY zones.name ASC
                LIMIT :limit
                OFFSET :offset
            """
    )
    fun getZones(
        limit: Int,
        offset: Int,
    ): Flow<List<ZoneWithRollCountEntity>>

    @Query("SELECT * FROM zones WHERE name = :name LIMIT 1")
    suspend fun findZoneByName(name: String): ZoneEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertZone(section: ZoneEntity): Long

    @Query("DELETE FROM zones WHERE id = :sectionId")
    suspend fun deleteZone(sectionId: Long)

    @Query(
        value = """
            SELECT COUNT(*) FROM zones
            """
    )
    fun getZonesCount(): Flow<Int>

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
    fun getFabricRollWithZone(rollId: Long): Flow<FabricRollWithZoneEntity?>

    @Query(
        value = """
            SELECT COUNT(*) FROM fabric_rolls
            WHERE zone_id = :zoneId
            """
    )
    fun getFabricRollsCount(zoneId: Long): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFabricRoll(fabricRoll: FabricRollEntity): Long

    @Query("DELETE FROM fabric_rolls WHERE id = :rollId")
    suspend fun deleteFabricRoll(rollId: Long)

    @Query("UPDATE fabric_rolls SET remaining_length = :remaining WHERE id = :rollId")
    suspend fun updateFabricRollRemainingLength(rollId: Long, remaining: Double)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReleaseHistory(releaseHistory: ReleaseHistoryEntity): Long

    @Query(
        value = """
            SELECT * FROM release_history
            WHERE roll_id = :rollId
            ORDER BY release_date DESC
            LIMIT :limit 
            OFFSET :offset
            """
    )
    fun getReleaseHistory(
        rollId: Long,
        limit: Int,
        offset: Int,
    ): Flow<List<ReleaseHistoryEntity>>

    @Query(
        value = """
            SELECT COUNT(*) FROM release_history
            WHERE roll_id = :rollId
            """
    )
    fun getReleaseHistoryCount(rollId: Long): Flow<Int>

    @Query("DELETE FROM release_history WHERE id = :releaseHistoryId")
    suspend fun deleteReleaseHistory(releaseHistoryId: Long)

    @Transaction
    suspend fun releaseFabricRollTransaction(
        releaseHistory: ReleaseHistoryEntity,
        rollId: Long,
        newRemainingLength: Double
    ) {
        insertReleaseHistory(releaseHistory)
        updateFabricRollRemainingLength(rollId, newRemainingLength)
    }

    @Query(
        value = """
            SELECT * FROM fabric_rolls 
            WHERE (CAST(id AS TEXT) LIKE :query AND :query GLOB '[0-9]*') 
            OR itemNo LIKE '%' || :query || '%' 
            OR orderNo LIKE '%' || :query || '%'
            ORDER BY id ASC
            LIMIT :limit
            OFFSET :offset
            """
    )
    suspend fun searchFabricRoll(
        query: String,
        limit: Int,
        offset: Int,
    ): List<FabricRollEntity>

    @Query(
        value = """
        SELECT COUNT(*) FROM fabric_rolls 
        WHERE (CAST(id AS TEXT) LIKE :query AND :query GLOB '[0-9]*') 
        OR itemNo LIKE '%' || :query || '%' 
        OR orderNo LIKE '%' || :query || '%'
        """
    )
    suspend fun getFabricRollSearchResultCount(query: String): Int
}
