package com.erp.jytextile.core.designsystem.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.theme.JyTheme

@Composable
fun <T> FixedTable(
    columnWidths: List<ColumnWidth.Fixed>,
    rows: List<T>,
    modifier: Modifier = Modifier,
    title: String = "",
    onRowClick: (T) -> Unit = {},
    headerActions: @Composable (RowScope.() -> Unit)? = null,
    headerRowContent: @Composable TableRowScope.(column: Int) -> Unit,
    rowContent: @Composable TableRowScope.(item: T, column: Int) -> Unit,
) {
    PanelSurface(modifier = modifier) {
        Table(
            title = title,
            columnWidths = columnWidths,
            rows = rows,
            horizontalScrollState = null,
            onRowClick = onRowClick,
            headerActions = headerActions,
            headerRowContent = headerRowContent,
            rowContent = rowContent,
        )
    }
}

@Composable
fun <T> ScrollableTable(
    columnWidths: List<ColumnWidth.Scrollable>,
    rows: List<T>,
    modifier: Modifier = Modifier,
    title: String = "",
    horizontalScrollState: ScrollState = rememberScrollState(),
    onRowClick: (T) -> Unit = {},
    headerActions: @Composable (RowScope.() -> Unit)? = null,
    headerRowContent: @Composable TableRowScope.(column: Int) -> Unit,
    rowContent: @Composable TableRowScope.(item: T, column: Int) -> Unit,
) {
    PanelSurface(modifier = modifier) {
        Table(
            title = title,
            columnWidths = columnWidths,
            rows = rows,
            onRowClick = onRowClick,
            horizontalScrollState = horizontalScrollState,
            headerActions = headerActions,
            headerRowContent = headerRowContent,
            rowContent = rowContent,
        )
    }
}

@Composable
internal fun <T> Table(
    columnWidths: List<ColumnWidth>,
    rows: List<T>,
    onRowClick: (T) -> Unit,
    modifier: Modifier = Modifier,
    title: String = "",
    horizontalScrollState: ScrollState?,
    headerActions: @Composable (RowScope.() -> Unit)? = null,
    headerRowContent: @Composable TableRowScope.(column: Int) -> Unit,
    rowContent: @Composable TableRowScope.(item: T, column: Int) -> Unit,
) {
    val tableScope = remember(columnWidths) { TableScopeImpl() }
    with(tableScope) {
        Column(modifier = modifier) {
            TableHeader(
                title = title,
                actions = headerActions,
            )
            TableHeaderRow(
                modifier = Modifier
                    .then(
                        if (horizontalScrollState == null) {
                            Modifier
                        } else {
                            Modifier.horizontalScroll(horizontalScrollState)
                        }
                    ),
                columnWidths = columnWidths,
                content = headerRowContent,
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (horizontalScrollState == null) {
                            Modifier
                        } else {
                            Modifier.horizontalScroll(horizontalScrollState)
                        }
                    ),
            ) {
                itemsIndexed(rows) { index, item ->
                    Column(
                        modifier = if (horizontalScrollState == null) {
                            Modifier
                        } else {
                            Modifier.width(IntrinsicSize.Min)
                        }
                    ) {
                        TableDataRow(
                            columnWidths = columnWidths,
                            onRowClick = { onRowClick(item) },
                            content = { column -> rowContent(item, column) },
                        )
                        if (index < rows.lastIndex) {
                            Divider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TableHeader(
    title: String,
    modifier: Modifier = Modifier,
    actions: @Composable (RowScope.() -> Unit)?,
) {
    Row(
        modifier = modifier
            .padding(
                vertical = 20.dp,
                horizontal = 24.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            style = JyTheme.typography.textLarge,
            fontWeight = FontWeight.SemiBold,
            color = JyTheme.color.heading,
            maxLines = 1,
            text = title,
        )
        if (actions != null) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                content = actions,
            )
        }
    }
}

@Composable
private fun TableScope.TableHeaderRow(
    columnWidths: List<ColumnWidth>,
    modifier: Modifier = Modifier,
    scrollState: ScrollState? = null,
    content: @Composable TableRowScope.(column: Int) -> Unit,
) {
    val dividerColor = JyTheme.color.divider
    Row(
        modifier = modifier
            .height(HeaderRowHeight)
            .fillMaxWidth()
            .then(if (scrollState == null) Modifier else Modifier.horizontalScroll(scrollState))
            .background(color = JyTheme.color.tableHeaderRowBackground)
            .drawWithContent {
                drawContent()
                drawLine(
                    color = dividerColor,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 1.dp.toPx(),
                )
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val tableRowScope = remember { TableRowScopeImpl(this@TableHeaderRow, this) }
        columnWidths.forEachIndexed { column, columnWidth ->
            tableRowScope.TableCell(
                columnWidth = columnWidth,
                column = column,
                content = { content(tableRowScope, column) }
            )
        }
    }
}

@Composable
private fun TableScope.TableDataRow(
    columnWidths: List<ColumnWidth>,
    onRowClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable TableRowScope.(column: Int) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onRowClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val tableRowScope = remember { TableRowScopeImpl(this@TableDataRow, this) }
        columnWidths.forEachIndexed { column, columnWidth ->
            tableRowScope.TableCell(
                columnWidth = columnWidth,
                column = column,
                content = { content(tableRowScope, column) }
            )
        }
    }
}

@Composable
private fun TableRowScope.TableCell(
    columnWidth: ColumnWidth,
    column: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .then(
                when (columnWidth) {
                    ColumnWidth.MaxIntrinsicWidth -> Modifier.columnMaxIntrinsicWidth(column)
                    is ColumnWidth.Size -> Modifier.requiredWidth(columnWidth.width)
                    is ColumnWidth.Weight -> Modifier.weight(columnWidth.weight)
                }
            ),
    ) { content() }
}

@Composable
private fun Divider(modifier: Modifier = Modifier) {
    HorizontalDivider(modifier = modifier, color = JyTheme.color.divider)
}

sealed interface ColumnWidth {
    sealed interface Scrollable : ColumnWidth
    sealed interface Fixed : ColumnWidth

    data class Size(val width: Dp) : Fixed, Scrollable

    data object MaxIntrinsicWidth : Fixed, Scrollable

    data class Weight(val weight: Float = 1.0f) : Fixed
}

interface TableScope {
    fun Modifier.columnMaxIntrinsicWidth(column: Int): Modifier
}

interface TableRowScope : TableScope, RowScope {

    @Composable
    fun HeaderCell(
        modifier: Modifier,
        header: String,
    )

    @Composable
    fun PrimaryTextCell(
        modifier: Modifier,
        text: String,
    )

    @Composable
    fun TextCell(
        modifier: Modifier,
        text: String,
    )

    @Composable
    fun IconButtonCell(
        modifier: Modifier,
        icon: ImageVector,
        onClick: () -> Unit,
    )
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

private class TableRowScopeImpl(
    tableScope: TableScope,
    rowScope: RowScope
) : TableRowScope, TableScope by tableScope, RowScope by rowScope {

    @Composable
    override fun HeaderCell(
        modifier: Modifier,
        header: String
    ) {
        Text(
            modifier = modifier
                .padding(horizontal = CellContentHorizontalPadding),
            style = JyTheme.typography.textSmall,
            fontWeight = FontWeight.SemiBold,
            color = JyTheme.color.tableHeading,
            maxLines = 1,
            text = header,
        )
    }

    @Composable
    override fun PrimaryTextCell(
        modifier: Modifier,
        text: String,
    ) {
        Text(
            modifier = modifier
                .padding(CellContentPadding),
            style = JyTheme.typography.textMedium,
            color = JyTheme.color.tablePrimaryColumn,
            maxLines = 1,
            text = text,
        )
    }

    @Composable
    override fun TextCell(
        modifier: Modifier,
        text: String,
    ) {
        Text(
            modifier = modifier
                .padding(CellContentPadding),
            style = JyTheme.typography.textMedium,
            color = JyTheme.color.body,
            maxLines = 1,
            text = text,
        )
    }

    @Composable
    override fun IconButtonCell(
        modifier: Modifier,
        icon: ImageVector,
        onClick: () -> Unit,
    ) {
        Icon(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .size(40.dp)
                .clickable(onClick = onClick)
                .padding(10.dp),
            imageVector = icon,
            tint = JyTheme.color.body,
            contentDescription = null,
        )
    }
}

private val HeaderRowHeight = 44.dp

private val CellContentHorizontalPadding: Dp = 24.dp
private val CellContentVerticalPadding: Dp = 16.dp

private val CellContentPadding: PaddingValues = PaddingValues(
    horizontal = CellContentHorizontalPadding,
    vertical = CellContentVerticalPadding,
)
