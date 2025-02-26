package com.erp.jytextile.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ZoneInsertRequest(
    val name: String,
)
