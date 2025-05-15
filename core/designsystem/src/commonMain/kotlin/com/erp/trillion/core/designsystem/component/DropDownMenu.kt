package com.erp.trillion.core.designsystem.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erp.trillion.core.designsystem.theme.TrillionTheme

@Composable
fun TrillionDropDownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    DropdownMenu(
        modifier = modifier,
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        containerColor = TrillionTheme.color.surface,
        tonalElevation = 0.dp,
        content = content,
    )
}
