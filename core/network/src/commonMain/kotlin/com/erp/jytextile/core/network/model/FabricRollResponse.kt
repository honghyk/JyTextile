package com.erp.jytextile.core.network.model

import com.erp.jytextile.core.domain.model.FabricRoll
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FabricRollResponse(
    val id: Long,
    @SerialName("zones") val zone: ZoneResponse,
    @SerialName("item_no") val itemNo: String,
    @SerialName("order_no") val orderNo: String?,
    val color: String?,
    val factory: String?,
    val finish: String?,
    @SerialName("remaining_quantity") val remainingQuantity: Double?,
    @SerialName("original_quantity") val originalQuantity: Double,
    val remark: String?
)

fun FabricRollResponse.toDomain() = FabricRoll(
    id = id,
    zone = zone.toDomain(),
    itemNo = itemNo,
    orderNo = orderNo.orEmpty(),
    color = color.orEmpty(),
    factory = factory.orEmpty(),
    finish = finish.orEmpty(),
    remainingQuantity = remainingQuantity ?: originalQuantity,
    originalQuantity = originalQuantity,
    remark = remark
)

fun FabricRoll.toResponse() = FabricRollResponse(
    id = id,
    zone = zone.toResponse(),
    itemNo = itemNo,
    orderNo = orderNo,
    color = color,
    factory = factory,
    finish = finish,
    remainingQuantity = remainingQuantity,
    originalQuantity = originalQuantity,
    remark = remark
)
