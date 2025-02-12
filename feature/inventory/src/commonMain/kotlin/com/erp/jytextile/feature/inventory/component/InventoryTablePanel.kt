package com.erp.jytextile.feature.inventory.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.component.JyButton
import com.erp.jytextile.core.designsystem.component.JyOutlinedButton
import com.erp.jytextile.core.designsystem.component.PanelSurface
import com.erp.jytextile.core.designsystem.component.Table
import com.erp.jytextile.core.designsystem.theme.JyTheme


@Composable
internal fun InventoryTablePanel(
    modifier: Modifier = Modifier
) {
    PanelSurface(
        modifier = modifier,
    ) {
        Column {
            InventoryTableHeader(
                modifier = Modifier.fillMaxWidth(),
                onAddProductClick = { /* TODO */ }
            )
            InventoryTable(
                modifier = Modifier.weight(1f),
                items = listOf("Product1", "Product2")
            )
            InventoryTableFooter(
                modifier = Modifier.fillMaxWidth(),
                currentPage = 1,
                totalPage = 10,
                onPreviousClick = { /* TODO */ },
                onNextClick = { /* TODO */ }
            )
        }
    }
}

@Composable
private fun InventoryTableHeader(
    onAddProductClick: () -> Unit,
    modifier: Modifier = Modifier,
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
            text = "Products"
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            JyButton(
                onClick = onAddProductClick,
            ) {
                Text(text = "Add Product")
            }
        }
    }
}

@Composable
private fun InventoryTable(
    items: List<String>,
    modifier: Modifier = Modifier,
) {
    Table(
        modifier = modifier,
        headers = listOf("Product", "Quantity"),
        items = items,
    ) {
        Text(text = it)
        Text(text = "10")
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
            Text("Previous")
        }
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = JyTheme.typography.textSmall,
            text = "Page $currentPage of $totalPage"
        )
        JyOutlinedButton(onClick = onNextClick) {
            Text("Next")
        }
    }
}
