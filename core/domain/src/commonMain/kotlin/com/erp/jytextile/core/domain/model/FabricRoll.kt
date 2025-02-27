package com.erp.jytextile.core.domain.model

import com.erp.jytextile.kotlin.utils.yardToMeter

data class FabricRoll(
    val id: Long,
    val zone: Zone,
    val itemNo: String,
    val orderNo: String,
    val color: String,
    val factory: String,
    val finish: String,
    val remainingQuantity: Double,
    val originalQuantity: Double,
    val remark: String,
) {
    constructor(
        id: Long,
        zoneId: Long,
        itemNo: String,
        orderNo: String,
        color: String,
        factory: String,
        finish: String,
        quantity: Double,
        lengthUnit: LengthUnit,
        remark: String
    ) : this(
        id = id,
        zone = Zone(zoneId),
        itemNo = itemNo,
        orderNo = orderNo,
        color = color,
        factory = factory,
        finish = finish,
        remainingQuantity = if (lengthUnit == LengthUnit.METER) quantity else quantity.yardToMeter(),
        originalQuantity = if (lengthUnit == LengthUnit.METER) quantity else quantity.yardToMeter(),
        remark = remark,
    )
}
