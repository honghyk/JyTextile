package com.erp.jytextile.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.core.domain.model.Zone

@Entity(
    tableName = "fabric_rolls",
    foreignKeys = [
        ForeignKey(
            entity = ZoneEntity::class,
            parentColumns = ["id"],
            childColumns = ["zone_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FabricRollEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("zone_id") val zoneId: Long,
    val code: String,
    val color: String,
    val factory: String,
    @ColumnInfo("remaining_length") val remainingLength: Int,
    @ColumnInfo("original_length") val originalLength: Int,
    val remark: String?,
)

fun FabricRollEntity.toDomain() = FabricRoll(
    id = id,
    zone = Zone(id = id),
    code = code,
    color = color,
    factory = factory,
    remainingLength = remainingLength,
    originalLength = originalLength,
    remark = remark,
)

fun FabricRoll.toEntity() = FabricRollEntity(
    id = id,
    zoneId = zone.id,
    code = code,
    color = color,
    factory = factory,
    remainingLength = remainingLength,
    originalLength = originalLength,
    remark = remark,
)
