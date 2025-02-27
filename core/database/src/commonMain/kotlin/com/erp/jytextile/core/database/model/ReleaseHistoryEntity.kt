package com.erp.jytextile.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.erp.jytextile.core.domain.model.ReleaseHistory
import kotlinx.datetime.Instant

@Entity(
    tableName = "release_histories",
    foreignKeys = [
        ForeignKey(
            entity = FabricRollEntity::class,
            parentColumns = ["id"],
            childColumns = ["roll_id"],
        )
    ],
    indices = [
        Index("roll_id")
    ]
)
data class ReleaseHistoryEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo("roll_id") val rollId: Long,
    val quantity: Double,
    @ColumnInfo("release_date") val releaseDate: Instant,
    val buyer: String,
    val remark: String?,
)

fun ReleaseHistoryEntity.toDomain() = ReleaseHistory(
    id = id,
    rollId = rollId,
    quantity = quantity,
    releaseDate = releaseDate,
    buyer = buyer,
    remark = remark.orEmpty(),
)

fun ReleaseHistory.toEntity() = ReleaseHistoryEntity(
    id = id,
    rollId = rollId,
    quantity = quantity,
    releaseDate = releaseDate,
    buyer = buyer,
    remark = remark,
)
