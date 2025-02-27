package com.erp.jytextile.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.erp.jytextile.core.domain.model.Zone

@Entity(
    tableName = "zones",
    indices = [Index(value = ["name"], unique = true)]
)
data class ZoneEntity(
    @PrimaryKey override val id: Long = 0,
    val name: String,
    @ColumnInfo(defaultValue = "0") val rollCount: Int = 0,
) : LocalEntity

fun ZoneEntity.toDomain() = Zone(
    id = id,
    name = name,
    rollCount = rollCount,
)

fun Zone.toEntity() = ZoneEntity(
    id = id,
    name = name,
    rollCount = rollCount,
)
