package com.erp.jytextile.core.designsystem

import ColumnWidth
import FixedTable
import ScrollableTable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.erp.jytextile.core.designsystem.component.PaginatedFixedTable
import com.erp.jytextile.core.designsystem.theme.JyTheme

@Composable
@Preview
private fun FixedTablePreview() {
    JyTheme {
        FixedTable(
            title = "Zones",
            columnWidths = listOf(
                ColumnWidth.Weight(1f),
                ColumnWidth.MaxIntrinsicWidth,
            ),
            rows = previewRows,
            headerRowContent = { column ->
                when (column) {
                    0 -> HeaderCell(modifier = Modifier, header = "Name")
                    1 -> HeaderCell(modifier = Modifier, header = "Qty")
                }
            },
            rowContent = { item, column ->
                when (column) {
                    0 -> PrimaryTextCell(modifier = Modifier, text = item.name)
                    1 -> TextCell(modifier = Modifier, text = item.qty)
                }
            }
        )
    }
}

@Composable
@Preview
private fun ScrollableTablePreview() {
    JyTheme {
        ScrollableTable(
            title = "Zones",
            columnWidths = listOf(
                ColumnWidth.MaxIntrinsicWidth,
                ColumnWidth.MaxIntrinsicWidth,
            ),
            rows = previewRows,
            headerRowContent = { column ->
                when (column) {
                    0 -> HeaderCell(modifier = Modifier, header = "Name")
                    1 -> HeaderCell(modifier = Modifier, header = "Qty")
                }
            },
            rowContent = { item, column ->
                when (column) {
                    0 -> PrimaryTextCell(modifier = Modifier, text = item.name)
                    1 -> TextCell(modifier = Modifier, text = item.qty)
                }
            }
        )
    }
}

@Composable
@Preview
private fun PaginatedFixedTablePreview() {
    JyTheme {
        PaginatedFixedTable(
            title = "Zones",
            columnWidths = listOf(
                ColumnWidth.Weight(1f),
                ColumnWidth.MaxIntrinsicWidth,
            ),
            rows = previewRows,
            onNext = { },
            onPrevious = { },
            headerRowContent = { column ->
                when (column) {
                    0 -> HeaderCell(modifier = Modifier, header = "Name")
                    1 -> HeaderCell(modifier = Modifier, header = "Qty")
                }
            },
            rowContent = { item, column ->
                when (column) {
                    0 -> PrimaryTextCell(modifier = Modifier, text = item.name)
                    1 -> TextCell(modifier = Modifier, text = item.qty)
                }
            }
        )
    }
}

private data class PreviewRow(
    val name: String,
    val qty: String,
)

private val previewRows = listOf(
    PreviewRow(name = "A-1", qty = "10"),
    PreviewRow(name = "A-2", qty = "20"),
    PreviewRow(name = "A-1", qty = "1022"),
    PreviewRow(name = "A-2", qty = "20"),
    PreviewRow(name = "A-1", qty = "1000"),
    PreviewRow(name = "A-2", qty = "20"),
    PreviewRow(name = "A-1", qty = "10"),
    PreviewRow(name = "A-2", qty = "20"),
)
