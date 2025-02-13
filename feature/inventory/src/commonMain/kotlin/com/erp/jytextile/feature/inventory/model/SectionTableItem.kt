package com.erp.jytextile.feature.inventory.model

import com.erp.jytextile.core.domain.model.Section

data class SectionTable(
    override val headers: List<String> = listOf(
        "ID",
        "Name",
        "Quantity",
    ),
    override val items: List<SectionTableItem> = emptyList(),
) : Table

data class SectionTableItem(
    val id: String,
    val name: String,
    val rollCount: Int,
    override val tableRow: List<String> = listOf(
        id,
        name,
        rollCount.toString(),
    )
) : TableItem

fun Section.toTableItem() = SectionTableItem(
    id = id.toString(),
    name = name,
    rollCount = rollCount,
)
