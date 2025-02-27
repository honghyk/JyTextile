package com.erp.jytextile.core.ui.model

import com.erp.jytextile.core.domain.model.ReleaseHistory
import com.erp.jytextile.kotlin.utils.formatDecimal
import com.erp.jytextile.kotlin.utils.meterToYard
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime


data class ReleaseHistoryTable(
    override val headers: List<String> = listOf(
        "NO",
        "BUYER",
        "QTY(M)",
        "QTY(Y)",
        "OUT DATE",
        "REMARK",
    ),
    override val items: List<ReleaseHistoryTableItem> = emptyList(),
    override val currentPage: Int = -1,
    override val totalPage: Int = -1,
) : Table

data class ReleaseHistoryTableItem(
    override val id: Long,
    val buyer: String,
    val qtyInMeter: String,
    val qtyInYard: String,
    val releaseDate: String,
    val remark: String,
    override val tableRow: List<String> = listOf(
        id.toString(),
        buyer,
        qtyInMeter,
        qtyInYard,
        releaseDate,
        remark,
    )
) : TableItem

fun ReleaseHistory.toTableItem() = ReleaseHistoryTableItem(
    id = id,
    buyer = buyer,
    qtyInMeter = quantity.formatDecimal(1),
    qtyInYard = quantity.meterToYard().formatDecimal(1),
    releaseDate = releaseDate.toLocalDateTime(TimeZone.currentSystemDefault()).format(
        LocalDateTime.Format {
            date(LocalDate.Formats.ISO)
        }
    ),
    remark = remark,
)
