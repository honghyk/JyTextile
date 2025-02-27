package com.erp.jytextile.core.network.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReleaseFabricRollRequest(
    @SerialName("roll_id") val rollId: Long,
    val quantity: Double,
    val buyer: String,
    @SerialName("release_at") val releaseAt: Instant,
    val remark: String?,
)
