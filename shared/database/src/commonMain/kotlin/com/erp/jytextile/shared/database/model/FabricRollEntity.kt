package com.erp.jytextile.shared.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.erp.jytextile.shared.domain.model.FabricRoll

@Entity(
    tableName = "fabric_rolls",
    foreignKeys = [
        ForeignKey(
            entity = SectionEntity::class,
            parentColumns = ["id"],
            childColumns = ["section_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FabricRollEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("section_id") val sectionId: Long,
    val code: String,
    val color: String,
    @ColumnInfo("remaining_length") val remainingLength: Int,
    @ColumnInfo("original_length") val originalLength: Int,
)

fun FabricRollEntity.toDomain() = FabricRoll(
    id = id,
    sectionId = sectionId,
    code = code,
    color = color,
    remainingLength = remainingLength,
    originalLength = originalLength,
)
