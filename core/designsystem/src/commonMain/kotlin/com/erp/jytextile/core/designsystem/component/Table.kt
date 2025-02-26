package com.erp.jytextile.core.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.theme.JyTheme

@Composable
fun Table(
    headers: List<String>,
    rows: List<List<String>>,
    onRowClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    headerCellContent: @Composable TableRowScope.(Int, String) -> Unit,
    rowCellContent: @Composable TableRowScope.(Int, Int, String) -> Unit,
) {
    val tableScope = remember(rows) { TableScopeImpl() }
    Column(modifier = modifier.fillMaxWidth()) {
        tableScope.TableHeader(
            headers = headers,
            content = headerCellContent,
        )
        HorizontalDivider(color = JyTheme.color.border)
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            itemsIndexed(rows) { index, row ->
                Column(modifier = Modifier.width(IntrinsicSize.Min)) {
                    tableScope.TableRow(
                        modifier = Modifier.border(1.dp, JyTheme.color.border),
                        row = row,
                        onRowClick = { onRowClick(index) },
                        content = { column, cell -> rowCellContent(index, column, cell) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TableScope.TableHeader(
    headers: List<String>,
    modifier: Modifier = Modifier,
    content: @Composable TableRowScope.(Int, String) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val tableRowScope = remember { TableRowScopeImpl(this) }

        CompositionLocalProvider(
            LocalTextStyle provides JyTheme.typography.textSmall.copy(
                color = JyTheme.color.tableHeading,
            )
        ) {
            headers.forEachIndexed { column, header ->
                Box(
                    modifier = Modifier
                        .columnMaxIntrinsicWidth(column)
                        .alignByBaseline()
                ) {
                    content(tableRowScope, column, header)
                }
            }
        }
    }
}

@Composable
private fun TableScope.TableRow(
    row: List<String>,
    onRowClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable TableRowScope.(Int, String) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onRowClick, indication = null, interactionSource = null)
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val tableRowScope = remember { TableRowScopeImpl(this) }

        ProvideTextStyle(JyTheme.typography.textSmall) {
            row.forEachIndexed { column, cell ->
                Box(
                    modifier = Modifier
                        .columnMaxIntrinsicWidth(column)
                        .alignByBaseline()
                ) {
                    content(tableRowScope, column, cell)
                }
            }
        }
    }
}

private interface TableScope {
    fun Modifier.columnMaxIntrinsicWidth(column: Int): Modifier
}

private class TableScopeImpl : TableScope {
    val columnWidths = mutableStateMapOf<Int, Int>()

    override fun Modifier.columnMaxIntrinsicWidth(column: Int) = layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        val currentMaxWidth = columnWidths[column] ?: 0
        val maxWidth = maxOf(currentMaxWidth, placeable.width)

        if (currentMaxWidth != maxWidth) {
            columnWidths[column] = maxWidth
        }

        layout(width = maxWidth, height = placeable.height) {
            placeable.placeRelative(0, 0)
        }
    }
}

interface TableRowScope : RowScope {

    @Composable
    fun TableCell(
        value: String,
        modifier: Modifier,
    )

    @Composable
    fun CheckBoxCell(
        checked: Boolean,
        onCheckChange: (Boolean) -> Unit,
        modifier: Modifier,
    )
}

private class TableRowScopeImpl(
    rowScope: RowScope,
) : TableRowScope, RowScope by rowScope {

    @Composable
    override fun TableCell(
        value: String,
        modifier: Modifier,
    ) {
        Text(
            modifier = modifier.padding(start = 16.dp, end = 48.dp),
            maxLines = 1,
            text = value,
        )
    }

    @Composable
    override fun CheckBoxCell(
        checked: Boolean,
        onCheckChange: (Boolean) -> Unit,
        modifier: Modifier
    ) {
        Checkbox(
            modifier = Modifier.padding(horizontal = 16.dp),
            checked = checked,
            onCheckedChange = onCheckChange,
        )
    }
}
