package com.erp.jytextile.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.theme.JyTheme

@Composable
fun <T> Table(
    headers: List<String>,
    items: List<T>,
    onRowClick: (T) -> Unit,
    modifier: Modifier = Modifier,
    tableRowContent: @Composable TableRowScope.(T) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        TableHeader(headers = headers)
        LazyColumn {
            items(items) { item ->
                HorizontalDivider(color = JyTheme.color.border)
                TableRow(
                    onRowClick = { onRowClick(item) }
                ) {
                    tableRowContent(item)
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
            .horizontalScroll(rememberScrollState())
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
            .horizontalScroll(rememberScrollState())
            .clickable(onClick = onRowClick)
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

private val MinimumCellWidth = 80.dp
