package com.erp.jytextile.feature.inventory.roll.model

import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.feature.inventory.common.model.Table
import com.erp.jytextile.feature.inventory.common.model.TableItem

data class RollTable(
    override val headers: List<String> = listOf(
        "NO",
        "ITEM NO",
        "COLOR",
        "FACTORY",
        "QTY",
        "TOTAL",
        "REMARK",
    ),
    override val items: List<RollTableItem> = emptyList(),
) : Table

data class RollTableItem(
    override val id: Long,
    val itemNo: String,
    val color: String,
    val factory: String,
    val qty: String,
    val total: String,
    val remark: String,
    override val tableRow: List<String> = listOf(
        id.toString(),
        itemNo,
        color,
        factory,
        qty,
        total,
        remark,
    )
) : TableItem

fun FabricRoll.toTableItem() = RollTableItem(
    id = id,
    itemNo = code,
    color = color,
    factory = factory,
    qty = remainingLength.toString(),
    total = originalLength.toString(),
    remark = remark ?: "",
)
