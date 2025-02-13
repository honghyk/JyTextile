package com.erp.jytextile.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.erp.jytextile.core.database.model.FabricRollEntity
import com.erp.jytextile.core.database.model.ReleaseHistoryEntity
import com.erp.jytextile.core.database.model.SectionEntity
import com.erp.jytextile.core.database.model.SectionWithRollCountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {

    @Query(
        value = """
                SELECT sections.*, COUNT(fabric_rolls.id) AS rollCount
                FROM sections
                LEFT JOIN fabric_rolls ON sections.id = fabric_rolls.section_id
                GROUP BY sections.id
                ORDER BY sections.name ASC
                LIMIT :limit
                OFFSET :offset
            """
    )
    fun getSections(
        limit: Int,
        offset: Int,
    ): Flow<List<SectionWithRollCountEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSection(section: SectionEntity): Long

    @Query("DELETE FROM sections WHERE id = :sectionId")
    suspend fun deleteSection(sectionId: Long)

    @Query(
        value = """
            SELECT COUNT(*) FROM sections
            """
    )
    fun getSectionsCount(): Flow<Int>

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
