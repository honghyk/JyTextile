package com.erp.trillion.core.domain.model

import com.erp.trillion.kotlin.utils.yardToMeter

data class FabricRoll(
    override val id: Long,
    val zone: Zone,
    val itemNo: String,
    val orderNo: String,
    val color: String,
    val factory: String,
    val finish: String,
    val remainingQuantity: Double,
    val originalQuantity: Double,
    val remark: String,
): Entity {
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
