package com.erp.jytextile.shared.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sections")
data class SectionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
)
