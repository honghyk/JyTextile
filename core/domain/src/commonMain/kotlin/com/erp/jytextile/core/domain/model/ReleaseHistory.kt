package com.erp.jytextile.core.domain.model

import kotlinx.datetime.Instant

data class ReleaseHistory(
    override val id: Long,
    val rollId: Long,
    val quantity: Double,
    val releaseDate: Instant,
    val buyer: String,
    val remark: String,
): Entity
