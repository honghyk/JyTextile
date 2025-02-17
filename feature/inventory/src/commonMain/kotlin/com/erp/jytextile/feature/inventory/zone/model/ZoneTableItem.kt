package com.erp.jytextile.feature.inventory.zone.model

import com.erp.jytextile.core.domain.model.Section
import com.erp.jytextile.feature.inventory.common.model.Table
import com.erp.jytextile.feature.inventory.common.model.TableItem

data class ZoneTable(
    override val headers: List<String> = listOf(
        "Name",
        "Quantity",
    ),
    override val items: List<ZoneTableItem> = emptyList(),
) : Table

data class ZoneTableItem(
    val name: String,
    val rollCount: Int,
    override val tableRow: List<String> = listOf(
        name,
        rollCount.toString(),
    )
) : TableItem

fun Section.toTableItem() = ZoneTableItem(
    name = name,
    rollCount = rollCount,
)
