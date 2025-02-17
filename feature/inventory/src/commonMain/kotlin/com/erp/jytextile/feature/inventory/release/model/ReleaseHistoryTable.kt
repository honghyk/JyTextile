package com.erp.jytextile.feature.inventory.release.model

import com.erp.jytextile.core.base.extension.meterToYard
import com.erp.jytextile.core.domain.model.ReleaseHistory
import com.erp.jytextile.feature.inventory.common.model.Table
import com.erp.jytextile.feature.inventory.common.model.TableItem
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import kotlin.math.round


data class ReleaseHistoryTable(
    override val headers: List<String> = listOf(
        "NO",
        "ORDER NO",
        "BUYER",
        "QTY(M)",
        "QTY(Y)",
        "OUT DATE",
    ),
    override val items: List<ReleaseHistoryTableItem> = emptyList(),
) : Table

data class ReleaseHistoryTableItem(
    override val id: Long,
    val orderNo: String,
    val buyer: String,
    val qtyInMeter: String,
    val qtyInYard: String,
    val releaseDate: String,
    override val tableRow: List<String> = listOf(
        id.toString(),
        orderNo,
        buyer,
        qtyInMeter,
        qtyInYard,
        releaseDate,
    )
) : TableItem

fun ReleaseHistory.toTableItem() = ReleaseHistoryTableItem(
    id = id,
    orderNo = orderNo,
    buyer = destination,
    qtyInMeter = (round(quantity * 10) / 10).toString(),
    qtyInYard = (round(meterToYard(quantity) * 10) / 10).toString(),
    releaseDate = releaseDate.toLocalDateTime(TimeZone.currentSystemDefault()).format(
        LocalDateTime.Format {
            date(LocalDate.Formats.ISO)
        }
    )
)
