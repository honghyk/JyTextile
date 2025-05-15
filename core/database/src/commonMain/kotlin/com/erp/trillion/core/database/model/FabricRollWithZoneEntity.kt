package com.erp.trillion.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.erp.trillion.core.domain.model.FabricRoll
import com.erp.trillion.core.domain.model.Zone

data class FabricRollWithZoneEntity(
    @Embedded val fabricRoll: FabricRollEntity,
    @ColumnInfo(name = "zone_name") val zoneName: String
)

fun FabricRollWithZoneEntity.toDomain() = FabricRoll(
    id = fabricRoll.id,
    zone = Zone(
        id = fabricRoll.zoneId,
        name = zoneName,
    ),
    itemNo = fabricRoll.itemNo,
    orderNo = fabricRoll.orderNo,
    color = fabricRoll.color,
    factory = fabricRoll.factory,
    remainingQuantity = fabricRoll.remainingLength,
    originalQuantity = fabricRoll.originalLength,
    finish = fabricRoll.finish,
    remark = fabricRoll.remark.orEmpty(),
)
