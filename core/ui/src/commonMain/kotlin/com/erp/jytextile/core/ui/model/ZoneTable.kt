package com.erp.jytextile.core.ui.model

import com.erp.jytextile.core.domain.model.Zone

data class ZoneTable(
    override val headers: List<String> = listOf(
        "Name",
        "Quantity",
    ),
    override val items: List<ZoneTableItem> = emptyList(),
) : Table

data class ZoneTableItem(
    override val id: Long,
    val name: String,
    val rollCount: Int,
    override val tableRow: List<String> = listOf(
        name,
        rollCount.toString(),
    )
) : TableItem

fun Zone.toTableItem() = ZoneTableItem(
    id = id,
    name = name,
    rollCount = rollCount,
)
