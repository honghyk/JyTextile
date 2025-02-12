package com.erp.jytextile.shared.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.erp.jytextile.shared.domain.model.Section

@Entity(tableName = "sections")
data class SectionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
)

fun SectionEntity.toDomain() = Section(
    id = id,
    name = name,
)
