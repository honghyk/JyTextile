package com.erp.jytextile.core.network.model

import com.erp.jytextile.core.domain.model.Zone
import kotlinx.serialization.Serializable

@Serializable
data class ZoneResponse(
    val id: Long,
    val name: String,
)

fun ZoneResponse.toDomain(): Zone = Zone(
    id = id,
    name = name,
)

fun Zone.toResponse(): ZoneResponse = ZoneResponse(
    id = id,
    name = name,
)
