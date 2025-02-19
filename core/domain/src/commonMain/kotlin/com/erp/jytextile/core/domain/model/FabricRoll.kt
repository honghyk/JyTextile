package com.erp.jytextile.core.domain.model

data class FabricRoll(
    val id: Long,
    val zone: Zone,
    val itemNo: String,
    val color: String,
    val factory: String,
    val remainingLength: Double,
    val originalLength: Double,
    val remark: String?,
)
