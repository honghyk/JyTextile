package com.erp.jytextile.shared.domain.model

data class FabricRoll(
    val id: Long,
    val sectionId: Long,
    val code: String,
    val color: String,
    val remainingLength: Int,
    val originalLength: Int,
)
