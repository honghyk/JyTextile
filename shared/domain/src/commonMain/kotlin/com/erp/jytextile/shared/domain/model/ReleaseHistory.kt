package com.erp.jytextile.shared.domain.model

import kotlinx.datetime.Instant

data class ReleaseHistory(
    val id: Long,
    val rollId: Long,
    val quantity: Double,
    val releaseDate: Instant,
    val destination: String,
)
