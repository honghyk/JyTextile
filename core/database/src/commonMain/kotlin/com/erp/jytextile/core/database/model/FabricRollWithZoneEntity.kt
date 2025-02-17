package com.erp.jytextile.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.core.domain.model.Zone

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
    code = fabricRoll.code,
    color = fabricRoll.color,
    factory = fabricRoll.factory,
    remainingLength = fabricRoll.remainingLength,
    originalLength = fabricRoll.originalLength,
    remark = fabricRoll.remark,
)
