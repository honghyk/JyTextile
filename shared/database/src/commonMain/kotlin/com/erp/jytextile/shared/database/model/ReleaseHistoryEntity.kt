package com.erp.jytextile.shared.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.erp.jytextile.shared.domain.model.ReleaseHistory
import kotlinx.datetime.Instant

@Entity(
    tableName = "release_history",
    foreignKeys = [
        ForeignKey(
            entity = FabricRollEntity::class,
            parentColumns = ["id"],
            childColumns = ["roll_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ReleaseHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("roll_id") val rollId: Long,
    val quantity: Double,
    @ColumnInfo("release_date") val releaseDate: Instant,
    val destination: String,
)

fun ReleaseHistoryEntity.toDomain() = ReleaseHistory(
    id = id,
    rollId = rollId,
    quantity = quantity,
    releaseDate = releaseDate,
    destination = destination,
)
