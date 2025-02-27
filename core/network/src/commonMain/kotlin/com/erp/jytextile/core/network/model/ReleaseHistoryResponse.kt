package com.erp.jytextile.core.network.model

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
