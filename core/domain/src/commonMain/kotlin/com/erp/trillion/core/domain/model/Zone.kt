package com.erp.trillion.core.domain.model

data class Zone(
    override val id: Long,
    val name: String = "",
    val rollCount: Int = 0,
): Entity
