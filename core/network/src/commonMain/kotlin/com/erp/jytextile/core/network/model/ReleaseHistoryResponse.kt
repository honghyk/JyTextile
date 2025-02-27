package com.erp.jytextile.core.network.model

import com.erp.jytextile.core.domain.model.ReleaseHistory
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ReleaseHistoryResponse(
    val id: Long,
    @SerialName("roll_id") val rollId: Long,
    val quantity: Double,
    @SerialName("release_at") val releaseAt: String,
    val buyer: String,
    val remark: String?,
)

fun ReleaseHistoryResponse.toDomain() = ReleaseHistory(
    id = id,
    rollId = rollId,
    quantity = quantity,
    releaseDate = DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET
        .parse(releaseAt)
        .toInstantUsingOffset(),
    buyer = buyer,
    remark = remark.orEmpty(),
)
