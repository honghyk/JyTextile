package com.erp.jytextile.shared.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erp.jytextile.shared.designsystem.theme.JyTheme
import com.erp.jytextile.shared.designsystem.theme.Palette

@Composable
fun <T> Table(
    headers: List<String>,
    items: List<T>,
    modifier: Modifier = Modifier,
    tableRowContent: @Composable RowScope.(T) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        TableHeader(headers = headers)
        LazyColumn {
            items(items) { item ->
                HorizontalDivider(
                    color = JyTheme.color.border,
                )
                TableRow {
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
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides JyTheme.typography.textSmall.copy(
                color = Palette.grey500
            )
        ) {
            headers.forEach { header ->
                Text(text = header)
            }
        }
    }
}

@Composable
private fun TableRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 14.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides JyTheme.typography.textSmall.copy(
                color = Palette.grey700,
            )
        ) {
            content()
        }
    }
}
