package com.erp.jytextile.core.ui.model

import com.erp.jytextile.core.domain.model.ReleaseHistory
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
    val buyer: String,
    val qtyInMeter: String,
    val qtyInYard: String,
    val releaseDate: String,
    override val tableRow: List<String> = listOf(
        id.toString(),
        buyer,
        qtyInMeter,
        qtyInYard,
        releaseDate,
    )
) : TableItem

fun ReleaseHistory.toTableItem() = ReleaseHistoryTableItem(
    id = id,
    buyer = destination,
    qtyInMeter = (round(quantity * 10) / 10).toString(),
    qtyInYard = (round(meterToYard(quantity) * 10) / 10).toString(),
    releaseDate = releaseDate.toLocalDateTime(TimeZone.currentSystemDefault()).format(
        LocalDateTime.Format {
            date(LocalDate.Formats.ISO)
        }
    )
)

// TODO: Move to kotlin util module
private fun meterToYard(meter: Double): Double {
    return meter * 1.09361
}
