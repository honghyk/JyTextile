package com.erp.jytextile.shared.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erp.jytextile.shared.database.model.FabricRollEntity
import com.erp.jytextile.shared.database.model.ReleaseHistoryEntity
import com.erp.jytextile.shared.database.model.SectionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {

    @Query("SELECT * FROM sections ORDER BY name ASC")
    fun getSections(): Flow<List<SectionEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSection(section: SectionEntity): Long

    @Query("DELETE FROM sections WHERE id = :sectionId")
    suspend fun deleteSection(sectionId: Long)

    @Query(
        value = """
            SELECT * FROM fabric_rolls
            WHERE section_id = :sectionId
            AND (:filterHasRemaining = 0 OR remaining_length > 0)
            ORDER BY code ASC
            LIMIT :limit 
            OFFSET :offset
            """
    )
    fun getFabricRolls(
        sectionId: Long,
        limit: Int,
        offset: Int,
        filterHasRemaining: Boolean,
    ): Flow<List<FabricRollEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFabricRoll(fabricRoll: FabricRollEntity): Long

    @Query("DELETE FROM fabric_rolls WHERE id = :rollId")
    suspend fun deleteFabricRoll(rollId: Long)

    @Query("UPDATE fabric_rolls SET remaining_length = :remaining WHERE id = :rollId")
    suspend fun updateFabricRollRemainingLength(rollId: Long, remaining: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReleaseHistory(releaseHistory: ReleaseHistoryEntity): Long

    @Query("SELECT * FROM release_history WHERE roll_id = :rollId ORDER BY release_date DESC")
    fun getReleaseHistory(rollId: Long): Flow<List<ReleaseHistoryEntity>>

    @Query("DELETE FROM release_history WHERE id = :releaseHistoryId")
    suspend fun deleteReleaseHistory(releaseHistoryId: Long)
}
