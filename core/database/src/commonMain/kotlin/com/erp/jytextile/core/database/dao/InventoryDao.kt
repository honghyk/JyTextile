package com.erp.jytextile.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.erp.jytextile.core.database.model.FabricRollEntity
import com.erp.jytextile.core.database.model.ReleaseHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {

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
            SELECT * FROM release_history
            WHERE id = :id
            LIMIT 1
            """
    )
    suspend fun getReleaseHistoryById(id: Long): ReleaseHistoryEntity?

    @Query(
        value = """
            SELECT COUNT(*) FROM release_history
            WHERE roll_id = :rollId
            """
    )
    fun getReleaseHistoryCount(rollId: Long): Flow<Int>

    @Transaction
    suspend fun deleteReleaseHistories(releaseHistoryIds: List<Long>) {
        releaseHistoryIds.forEach { id ->
            deleteReleaseHistoryTransaction(id)
        }
    }

    @Transaction
    suspend fun deleteReleaseHistoryTransaction(id: Long) {
        val releaseHistory = getReleaseHistoryById(id) ?: return
        val remainingLength = getFabricRollRemainingLength(releaseHistory.rollId)

        deleteReleaseHistory(id)
        updateFabricRollRemainingLength(
            releaseHistory.rollId,
            remainingLength + releaseHistory.quantity
        )
    }

    @Query("DELETE FROM release_history WHERE id = :id")
    suspend fun deleteReleaseHistory(id: Long)

    @Transaction
    suspend fun releaseFabricRollTransaction(
        releaseHistory: ReleaseHistoryEntity,
        rollId: Long,
    ) {
        val remainingLength = getFabricRollRemainingLength(rollId)
        insertReleaseHistory(releaseHistory)
        updateFabricRollRemainingLength(rollId, remainingLength - releaseHistory.quantity)
    }

    @Query("SELECT remaining_length FROM fabric_rolls WHERE id = :rollId")
    suspend fun getFabricRollRemainingLength(rollId: Long): Double

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
