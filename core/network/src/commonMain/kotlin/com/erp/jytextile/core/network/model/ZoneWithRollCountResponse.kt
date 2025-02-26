package com.erp.jytextile.core.network.model

import com.erp.jytextile.core.domain.model.Zone
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ZoneWithRollCountResponse(
    val id: Long,
    val name: String,
    @SerialName("fabric_rolls") val rollCount: List<FabricRollCountResponse>,
)

fun ZoneWithRollCountResponse.toDomain(): Zone = Zone(
    id = id,
    name = name,
    rollCount = rollCount.firstOrNull()?.count ?: 0,
)
