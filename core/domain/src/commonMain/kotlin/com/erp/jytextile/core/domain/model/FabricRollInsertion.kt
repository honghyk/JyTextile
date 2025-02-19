package com.erp.jytextile.core.domain.model

data class FabricRollInsertion(
    val id: Long,
    val itemNo: String,
    val orderNo: String,
    val color: String,
    val factory: String,
    val quantity: Double,
    val remark: String,
) {
    constructor(
        id: Long,
        itemNo: String,
        orderNo: String,
        color: String,
        factory: String,
        quantity: Double,
        lengthUnit: LengthUnit,
        remark: String
    ) : this(
        id = id,
        itemNo = itemNo,
        orderNo = orderNo,
        color = color,
        factory = factory,
        quantity = if (lengthUnit == LengthUnit.METER) quantity else yardToMeter(quantity),
        remark = remark,
    )
}

// TODO: Move to kotlin util module
private fun yardToMeter(yard: Double): Double {
    return yard * 0.9144
}
