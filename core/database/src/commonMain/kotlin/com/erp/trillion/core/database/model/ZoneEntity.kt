package com.erp.trillion.core.database.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.erp.trillion.core.domain.model.Zone

@Entity(
    tableName = "zones",
    indices = [Index(value = ["name"], unique = true)]
)
data class ZoneEntity(
    @PrimaryKey override val id: Long,
    val name: String,
    val rollCount: Int,
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
