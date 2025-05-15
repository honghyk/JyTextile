package com.erp.trillion.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.erp.trillion.core.domain.model.FabricRoll
import com.erp.trillion.core.domain.model.Zone

@Entity(
    tableName = "fabric_rolls",
    foreignKeys = [
        ForeignKey(
            entity = ZoneEntity::class,
            parentColumns = ["id"],
            childColumns = ["zone_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("zone_id")
    ]
)
data class FabricRollEntity(
    @PrimaryKey override val id: Long = 0,
    @ColumnInfo("zone_id") val zoneId: Long,
    val itemNo: String,
    val orderNo: String,
    val color: String,
    val factory: String,
    val finish: String,
    @ColumnInfo("remaining_length") val remainingLength: Double,
    @ColumnInfo("original_length") val originalLength: Double,
    val remark: String?,
): LocalEntity

fun FabricRollEntity.toDomain() = FabricRoll(
    id = id,
    zone = Zone(id = id),
    itemNo = itemNo,
    orderNo = orderNo,
    color = color,
    factory = factory,
    finish = finish,
    remainingQuantity = remainingLength,
    originalQuantity = originalLength,
    remark = remark.orEmpty(),
)

fun FabricRoll.toEntity() = FabricRollEntity(
    id = id,
    zoneId = zone.id,
    itemNo = itemNo,
    orderNo = orderNo,
    color = color,
    factory = factory,
    finish = finish,
    remainingLength = remainingQuantity,
    originalLength = originalQuantity,
    remark = remark,
)
