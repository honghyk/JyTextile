package com.erp.jytextile.core.domain.model

data class FabricRoll(
    val id: Long,
    val zoneId: Long,
    val code: String,
    val color: String,
    val factory: String,
    val remainingLength: Int,
    val originalLength: Int,
    val remark: String?,
)
