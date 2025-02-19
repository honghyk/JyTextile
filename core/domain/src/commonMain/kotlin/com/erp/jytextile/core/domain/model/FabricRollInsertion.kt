package com.erp.jytextile.core.domain.model

import com.erp.jytextile.kotlin.utils.yardToMeter

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
        quantity = if (lengthUnit == LengthUnit.METER) quantity else quantity.yardToMeter(),
        remark = remark,
    )
}
