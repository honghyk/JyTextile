package com.erp.jytextile.core.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.erp.jytextile.core.domain.model.Zone

@Entity(
    tableName = "zones",
    indices = [Index(value = ["name"], unique = true)]
)
data class ZoneEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
)

data class ZoneWithRollCountEntity(
    @Embedded val zone: ZoneEntity,
    val rollCount: Int,
)

fun ZoneWithRollCountEntity.toDomain() = Zone(
    id = zone.id,
    name = zone.name,
    rollCount = rollCount,
)
