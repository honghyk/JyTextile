package com.erp.jytextile.core.domain.model

data class FabricRoll(
    val id: Long,
    val zone: Zone,
    val itemNo: String,
    val orderNo: String,
    val color: String,
    val factory: String,
    val remainingQuantity: Double,
    val originalQuantity: Double,
    val remark: String?,
)
