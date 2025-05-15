package com.erp.trillion.core.ui.model

import com.erp.trillion.core.domain.model.FabricRoll
import com.erp.trillion.core.domain.model.Zone
import com.erp.trillion.kotlin.utils.meterToYard

data class FabricRollUiModel(
    val id: Long,
    val zone: Zone,
    val itemNo: String,
    val orderNo: String,
    val color: String,
    val factory: String,
    val remainingQuantity: Double,
    val originalQuantity: Double,
    val finish: String,
    val remark: String?,
) {
    val remainingQuantityInYard: Double = remainingQuantity.meterToYard()
    val originalQuantityInYard: Double = originalQuantity.meterToYard()
}

fun FabricRoll.toUiModel() = FabricRollUiModel(
    id = id,
    zone = zone,
    itemNo = itemNo,
    orderNo = orderNo,
    color = color,
    factory = factory,
    remainingQuantity = remainingQuantity,
    originalQuantity = originalQuantity,
    finish = finish,
    remark = remark,
)
