package com.erp.jytextile.core.ui.model

import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.kotlin.utils.formatDecimal
import com.erp.jytextile.kotlin.utils.meterToYard

data class RollTable(
    override val headers: List<String> = listOf(
        "NO",
        "ORDER NO",
        "ITEM NO",
        "COLOR",
        "FACTORY",
        "QTY(M)",
        "QTY(Y)",
        "FINISH",
        "REMARK",
        "",
    ),
    override val items: List<RollTableItem> = emptyList(),
) : Table

data class RollTableItem(
    override val id: Long,
    val orderNo: String,
    val itemNo: String,
    val color: String,
    val factory: String,
    val qtyInMeter: String,
    val qtyInYard: String,
    val total: String,
    val finish: String,
    val remark: String,
    override val tableRow: List<String> = listOf(
        id.toString(),
        orderNo,
        itemNo,
        color,
        factory,
        qtyInMeter,
        qtyInYard,
        finish,
        remark,
    )
) : TableItem

fun FabricRoll.toTableItem() = RollTableItem(
    id = id,
    orderNo = orderNo,
    itemNo = itemNo,
    color = color,
    factory = factory,
    qtyInMeter = remainingQuantity.formatDecimal(1),
    qtyInYard = remainingQuantity.meterToYard().formatDecimal(1),
    total = originalQuantity.toString(),
    finish =  finish,
    remark = remark ?: "",
)
