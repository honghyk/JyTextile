package com.erp.jytextile.feature.inventory.zone.model

import com.erp.jytextile.core.domain.model.Zone
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
