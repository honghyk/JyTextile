package com.erp.trillion.core.network.model

import com.erp.trillion.core.domain.model.FabricRoll
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FabricRollInsertRequest(
    val id: Long,
    @SerialName("zone_id") val zoneId: Long,
    @SerialName("item_no") val itemNo: String,
    @SerialName("order_no") val orderNo: String?,
    val color: String?,
    val factory: String?,
    val finish: String?,
    @SerialName("remaining_quantity") val quantity: Double,
    @SerialName("original_quantity") val originalQuantity: Double,
    val remark: String?,
)

fun FabricRoll.toRequest() = FabricRollInsertRequest(
    id = id,
    zoneId = zone.id,
    itemNo = itemNo,
    orderNo = orderNo.ifEmpty { null },
    color = color.ifEmpty { null },
    factory = factory.ifEmpty { null },
    finish = finish.ifEmpty { null },
    quantity = remainingQuantity,
    originalQuantity = originalQuantity,
    remark = remark.ifEmpty { null },
)
