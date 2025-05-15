package com.erp.trillion.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ZoneInsertRequest(
    val name: String,
)
