package com.erp.jytextile.core.ui.model

import com.erp.jytextile.core.domain.model.FabricRoll
import kotlin.math.round

data class RollTable(
    override val headers: List<String> = listOf(
        "NO",
        "ITEM NO",
        "COLOR",
        "FACTORY",
        "QTY(M)",
        "QTY(Y)",
        "REMARK",
    ),
    override val items: List<RollTableItem> = emptyList(),
) : Table

data class RollTableItem(
    override val id: Long,
    val itemNo: String,
    val color: String,
    val factory: String,
    val qtyInMeter: String,
    val qtyInYard: String,
    val total: String,
    val remark: String,
    override val tableRow: List<String> = listOf(
        id.toString(),
        itemNo,
        color,
        factory,
        qtyInMeter,
        qtyInYard,
        remark,
    )
) : TableItem

fun FabricRoll.toTableItem() = RollTableItem(
    id = id,
    itemNo = itemNo,
    color = color,
    factory = factory,
    qtyInMeter = (round(remainingQuantity * 10) / 10).toString(),
    qtyInYard = (round(meterToYard(remainingQuantity) * 10) / 10).toString(),
    total = originalQuantity.toString(),
    remark = remark ?: "",
)

// TODO: Move to kotlin util module
private fun meterToYard(meter: Double): Double {
    return meter * 1.09361
}
