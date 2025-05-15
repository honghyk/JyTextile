package com.erp.trillion.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.erp.trillion.core.database.model.FabricRollEntity
import com.erp.trillion.core.database.model.ReleaseHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReleaseHistoryDao {

    @Query("SELECT * FROM release_histories WHERE roll_id = :rollId ORDER BY release_date DESC")
    fun getReleaseHistories(rollId: Long): Flow<List<ReleaseHistoryEntity>>

    @Query("SELECT * FROM release_histories WHERE id = :id LIMIT 1")
    suspend fun getReleaseHistoryById(id: Long): ReleaseHistoryEntity?

    @Transaction
    suspend fun insertTransaction(releaseHistory: ReleaseHistoryEntity) {
        val remaining = getFabricRoll(releaseHistory.rollId).remainingLength
        insert(releaseHistory)
        updateFabricRollRemainingLength(releaseHistory.rollId, remaining - releaseHistory.quantity)
    }

    @Transaction
    suspend fun insertTransaction(releaseHistories: List<ReleaseHistoryEntity>) {
        val rollId = releaseHistories.firstOrNull()?.rollId ?: return
        val remaining = getFabricRoll(rollId).remainingLength
        insert(releaseHistories)
        updateFabricRollRemainingLength(rollId, remaining - releaseHistories.sumOf { it.quantity })
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(releaseHistory: ReleaseHistoryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(releaseHistories: List<ReleaseHistoryEntity>)

    @Transaction
    suspend fun deleteAllTransaction(rollId: Long) {
        val roll = getFabricRoll(rollId)
        deleteAll(rollId)
        updateFabricRollRemainingLength(rollId, roll.originalLength)
    }

    @Transaction
    suspend fun deleteTransaction(rollId: Long, ids: List<Long>) {
        ids.forEach { id -> deleteTransaction(rollId, id) }
    }

    @Transaction
    suspend fun deleteTransaction(rollId: Long, id: Long) {
        val releaseHistory = getReleaseHistoryById(id) ?: return
        val remaining = getFabricRoll(rollId).remainingLength

        delete(id)
        updateFabricRollRemainingLength(rollId, remaining + releaseHistory.quantity)
    }

    @Query("DELETE FROM release_histories WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM release_histories WHERE roll_id = :rollId")
    suspend fun deleteAll(rollId: Long)

    @Query("SELECT * FROM fabric_rolls WHERE id = :rollId")
    suspend fun getFabricRoll(rollId: Long): FabricRollEntity

    @Query("UPDATE fabric_rolls SET remaining_length = :remaining WHERE id = :rollId")
    suspend fun updateFabricRollRemainingLength(rollId: Long, remaining: Double)
}
