package com.erp.jytextile.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.theme.JyTheme
import com.seanproctor.datatable.DataColumn
import com.seanproctor.datatable.TableColumnWidth
import com.seanproctor.datatable.material3.DataTable

@Composable
fun <T> Table(
    columns: List<String>,
    rows: List<T>,
    onRowClick: (T) -> Unit,
    modifier: Modifier = Modifier,
    isWrapColumnWidth: Boolean = true,
    rowContent: com.seanproctor.datatable.TableRowScope.(T) -> Unit,
) {
    val dataColumns: List<DataColumn> by remember(columns) {
        mutableStateOf(
            columns.mapIndexed { index, column ->
                DataColumn(
                    width = when {
                        index == columns.lastIndex -> TableColumnWidth.Flex(1f)
                        isWrapColumnWidth -> TableColumnWidth.Wrap
                        else -> TableColumnWidth.Fraction(1.0f / columns.size)
                    },
                    header = {
                        Text(
                            style = JyTheme.typography.textSmall,
                            color = JyTheme.color.tableHeading,
                            text = column,
                        )
                    }
                )
            }
        )
    }
    DataTable(
        modifier = modifier,
        columns = dataColumns,
        headerHeight = 44.dp,
        rowHeight = 44.dp,
        separator = { HorizontalDivider(color = JyTheme.color.border) },
        contentPadding = PaddingValues(horizontal = 16.dp),
        content = {
            rows.forEach { row ->
                row {
                    onClick = { onRowClick(row) }
                    rowContent(row)
                }
            }
        },
    )
}

@Composable
fun <T> Table(
    headers: List<String>,
    items: List<T>,
    onRowClick: (T) -> Unit,
    modifier: Modifier = Modifier,
    tableRowContent: @Composable TableRowScope.(T) -> Unit,
) {
    Column(modifier = modifier.horizontalScroll(rememberScrollState())) {
        TableHeader(headers = headers)
        LazyColumn {
            items(items) { item ->
                Column(modifier = Modifier.width(IntrinsicSize.Min)) {
                    TableRow(
                        onRowClick = { onRowClick(item) },
                        content = { tableRowContent(item) }
                    )
                    HorizontalDivider(color = JyTheme.color.border)
                }
            }
        }
    }
}

@Composable
private fun TableHeader(
    headers: List<String>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
//            .horizontalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides JyTheme.typography.textSmall.copy(
                color = JyTheme.color.tableHeading,
            )
        ) {
            headers.forEach { header ->
                Text(
                    modifier = Modifier.widthIn(min = MinimumCellWidth),
                    text = header
                )
            }
        }
    }
}

@Composable
private fun TableRow(
    onRowClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable TableRowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
//            .horizontalScroll(rememberScrollState())
            .clickable(onClick = onRowClick, indication = null, interactionSource = null)
            .padding(
                horizontal = 16.dp,
                vertical = 14.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        val tableRowScope = remember { TableRowScopeImpl(this) }
        CompositionLocalProvider(
            LocalTextStyle provides JyTheme.typography.textSmall
        ) {
            content(tableRowScope)
        }
    }
}

interface TableRowScope : RowScope {

    @Composable
    fun TableCell(
        value: String,
        modifier: Modifier,
    )
}

private class TableRowScopeImpl(rowScope: RowScope) : TableRowScope, RowScope by rowScope {

    @Composable
    override fun TableCell(
        value: String,
        modifier: Modifier,
    ) {
        Text(
            modifier = modifier
                .widthIn(min = MinimumCellWidth)
                .alignByBaseline(),
            maxLines = 1,
            text = value,
        )
    }
}

private val MinimumCellWidth = 120.dp
