package com.erp.jytextile.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.component.JyOutlinedButton
import com.erp.jytextile.core.designsystem.component.PanelSurface
import com.erp.jytextile.core.designsystem.component.Table
import com.erp.jytextile.core.designsystem.theme.JyTheme
import com.erp.jytextile.core.ui.model.Table
import com.erp.jytextile.core.ui.model.TableItem

@Composable
fun TablePanel(
    table: Table,
    onItemClick: (TableItem) -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = "",
    selectedRows: List<TableItem>? = null,
    onItemSelected: ((Boolean, TableItem) -> Unit)? = null,
    titleActionButtons: @Composable (RowScope.() -> Unit)? = null,
) {
    TablePanel(
        modifier = modifier,
        table = table,
        currentPage = table.currentPage,
        totalPage = table.totalPage,
        selectedRows = selectedRows,
        onItemSelected = onItemSelected,
        onItemClick = onItemClick,
        onPreviousClick = onPreviousClick,
        onNextClick = onNextClick,
        title = { Text(text = title, maxLines = 1) },
        titleActionButtons = titleActionButtons,
    )
}

@Composable
fun TablePanel(
    table: Table,
    currentPage: Int,
    totalPage: Int,
    onItemClick: (TableItem) -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
    selectedRows: List<TableItem>? = null,
    onItemSelected: ((Boolean, TableItem) -> Unit)? = null,
    title: @Composable () -> Unit,
    titleActionButtons: @Composable (RowScope.() -> Unit)? = null,
) {
    PanelSurface(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            TableTitle(
                modifier = Modifier.fillMaxWidth(),
                title = title,
                titleActionButtons = titleActionButtons,
            )
            TableContent(
                modifier = Modifier.weight(1f),
                table = table,
                selectedRows = selectedRows,
                onItemClick = onItemClick,
                onItemSelected = onItemSelected,
            )
            TableFooter(
                modifier = Modifier.fillMaxWidth(),
                currentPage = currentPage + 1,
                totalPage = totalPage,
                onPreviousClick = onPreviousClick,
                onNextClick = onNextClick
            )
        }
    }
}

@Composable
private fun TableTitle(
    title: @Composable () -> Unit,
    titleActionButtons: @Composable (RowScope.() -> Unit)?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(
            top = 20.dp,
            start = 16.dp,
            end = 16.dp,
            bottom = 8.dp,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides JyTheme.typography.textXLarge.copy(
                color = JyTheme.color.heading,
            ),
            title
        )
        Spacer(modifier = Modifier.weight(1f))
        if (titleActionButtons != null) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                content = titleActionButtons,
            )
        }
    }
}

@Composable
private fun TableContent(
    table: Table,
    selectedRows: List<TableItem>?,
    onItemClick: (TableItem) -> Unit,
    onItemSelected: ((Boolean, TableItem) -> Unit)?,
    modifier: Modifier = Modifier
) {
    val tableHeaders = remember(table.headers, onItemSelected) {
        if (onItemSelected == null) table.headers else listOf("") + table.headers
    }
    val tableRows = remember(table.items, onItemSelected) {
        table.items.map {
            if (onItemSelected == null) it.tableRow else listOf("") + it.tableRow
        }
    }
    Table(
        modifier = modifier,
        headers = tableHeaders,
        rows = tableRows,
        onRowClick = { onItemClick(table.items[it]) },
        headerCellContent = { _, header ->
            TableCell(
                modifier = Modifier,
                value = header,
            )
        },
        rowCellContent = { row, column, cell ->
            if (onItemSelected != null && column == 0) {
                val selected = selectedRows?.contains(table.items[row]) ?: false
                CheckBoxCell(
                    modifier = Modifier,
                    checked = selected,
                    onCheckChange = { onItemSelected(!selected, table.items[row]) },
                )
            } else {
                TableCell(
                    modifier = Modifier,
                    value = cell,
                )
            }
        }
    )
}

@Composable
private fun TableFooter(
    currentPage: Int,
    totalPage: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 12.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        JyOutlinedButton(onClick = onPreviousClick) {
            Text(
                maxLines = 1,
                text = "이전"
            )
        }
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = JyTheme.typography.textSmall,
            maxLines = 1,
            text = if (totalPage > 0) "페이지 $currentPage of $totalPage" else "",
        )
        JyOutlinedButton(onClick = onNextClick) {
            Text(
                maxLines = 1,
                text = "다음"
            )
        }
    }
}
