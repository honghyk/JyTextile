package com.erp.trillion.core.network.model

import com.erp.trillion.core.domain.model.Zone
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
