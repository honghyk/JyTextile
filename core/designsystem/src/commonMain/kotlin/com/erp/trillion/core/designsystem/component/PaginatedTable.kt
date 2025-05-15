package com.erp.trillion.core.designsystem.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erp.trillion.core.designsystem.icon.TrillionIcons
import com.erp.trillion.core.designsystem.theme.Palette
import com.erp.trillion.core.designsystem.theme.TrillionTheme
import org.jetbrains.compose.resources.vectorResource

@Composable
fun <T> PaginatedFixedTable(
    columnWidths: List<ColumnWidth.Fixed>,
    rows: List<T>,
    modifier: Modifier = Modifier,
    title: String = "",
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onRowClick: (T) -> Unit = {},
    headerActions: @Composable (RowScope.() -> Unit)? = null,
    headerRowContent: @Composable TableRowScope.(column: Int) -> Unit,
    rowContent: @Composable TableRowScope.(item: T, column: Int) -> Unit,
) {
    PanelSurface(modifier = modifier) {
        Column {
            Table(
                modifier = Modifier.weight(1f),
                title = title,
                columnWidths = columnWidths,
                rows = rows,
                onRowClick = onRowClick,
                horizontalScrollState = null,
                headerActions = headerActions,
                headerRowContent = headerRowContent,
                rowContent = rowContent,
            )
            HorizontalDivider(color = TrillionTheme.color.divider)
            Pagination(
                onNext = onNext,
                onPrevious = onPrevious,
            )
        }
    }
}

@Composable
fun <T> PaginatedScrollableTable(
    columnWidths: List<ColumnWidth.Scrollable>,
    rows: List<T>,
    modifier: Modifier = Modifier,
    title: String = "",
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    horizontalScrollState: ScrollState = rememberScrollState(),
    onRowClick: (T) -> Unit = {},
    headerActions: @Composable (RowScope.() -> Unit)? = null,
    headerRowContent: @Composable TableRowScope.(column: Int) -> Unit,
    rowContent: @Composable TableRowScope.(item: T, column: Int) -> Unit,
) {
    PanelSurface(modifier = modifier) {
        Column {
            Table(
                modifier = Modifier.weight(1f),
                title = title,
                columnWidths = columnWidths,
                rows = rows,
                onRowClick = onRowClick,
                horizontalScrollState = horizontalScrollState,
                headerActions = headerActions,
                headerRowContent = headerRowContent,
                rowContent = rowContent,
            )
            HorizontalDivider(color = TrillionTheme.color.divider)
            Pagination(
                onNext = onNext,
                onPrevious = onPrevious,
            )
        }
    }
}

@Composable
private fun Pagination(
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 12.dp,
                bottom = 16.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PaginationButton(onClick = onPrevious) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = vectorResource(resource = TrillionIcons.ArrowLeft),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                maxLines = 1,
                text = "이전",
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        PaginationButton(onClick = onNext) {
            Text(
                maxLines = 1,
                text = "다음",
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = vectorResource(resource = TrillionIcons.ArrowRight),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun PaginationButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    TrillionOutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = TrillionButtonDefaults.outlinedButtonColors().copy(
            contentColor = Palette.grey700,
        ),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        content = {
            ProvideTextStyle(TrillionTheme.typography.textMedium) {
                content()
            }
        }
    )
}
