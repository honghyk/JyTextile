package com.erp.jytextile.feature.inventory.common.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.component.JyOutlinedButton
import com.erp.jytextile.core.designsystem.component.PanelSurface
import com.erp.jytextile.core.designsystem.component.Table
import com.erp.jytextile.core.designsystem.theme.JyTheme
import com.erp.jytextile.feature.inventory.common.model.Table
import com.erp.jytextile.feature.inventory.common.model.TableItem


@Composable
internal fun InventoryTablePanel(
    title: String,
    table: Table,
    currentPage: Int,
    totalPage: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
    headerButtonContent: @Composable RowScope.() -> Unit,
) {
    PanelSurface(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            InventoryTableHeader(
                modifier = Modifier.fillMaxWidth(),
                title = title,
                headerButtonContent = headerButtonContent,
            )
            InventoryTable(
                modifier = Modifier.weight(1f),
                headers = table.headers,
                items = table.items,
            )
            if (totalPage > 0) {
                InventoryTableFooter(
                    modifier = Modifier.fillMaxWidth(),
                    currentPage = currentPage,
                    totalPage = totalPage,
                    onPreviousClick = onPreviousClick,
                    onNextClick = onNextClick
                )
            }
        }
    }
}

@Composable
private fun InventoryTableHeader(
    title: String,
    modifier: Modifier = Modifier,
    headerButtonContent: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .padding(
                top = 20.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp,
            ),
        verticalAlignment = Alignment.Bottom,
    ) {
        Text(
            style = JyTheme.typography.textXLarge,
            color = JyTheme.color.heading,
            maxLines = 1,
            text = title,
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            content = headerButtonContent,
        )
    }
}

@Composable
private fun <T : TableItem> InventoryTable(
    headers: List<String>,
    items: List<T>,
    modifier: Modifier = Modifier,
) {
    Table(
        modifier = modifier,
        headers = headers,
        items = items,
    ) { item ->
        item.tableRow.forEach {
            TableCell(
                modifier = Modifier,
                value = it
            )
        }
    }
}

@Composable
private fun InventoryTableFooter(
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
                text = "Previous"
            )
        }
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = JyTheme.typography.textSmall,
            maxLines = 1,
            text = "Page $currentPage of $totalPage"
        )
        JyOutlinedButton(onClick = onNextClick) {
            Text(
                maxLines = 1,
                text = "Next"
            )
        }
    }
}
