package com.erp.jytextile.core.domain.model

import kotlinx.datetime.Instant

data class ReleaseHistory(
    val id: Long,
    val rollId: Long,
    val orderNo: String,
    val quantity: Double,
    val releaseDate: Instant,
    val destination: String,
)
