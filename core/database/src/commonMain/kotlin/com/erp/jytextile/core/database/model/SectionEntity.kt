package com.erp.jytextile.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.erp.jytextile.core.domain.model.Section

@Entity(tableName = "sections")
data class SectionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
)

fun SectionEntity.toDomain() = Section(
    id = id,
    name = name,
)
