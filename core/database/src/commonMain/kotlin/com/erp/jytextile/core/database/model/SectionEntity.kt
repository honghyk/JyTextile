package com.erp.jytextile.core.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.erp.jytextile.core.domain.model.Section

@Entity(tableName = "sections")
data class SectionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
)

data class SectionWithRollCountEntity(
    @Embedded val section: SectionEntity,
    val rollCount: Int,
)

fun SectionWithRollCountEntity.toDomain() = Section(
    id = section.id,
    name = section.name,
    rollCount = rollCount,
)
