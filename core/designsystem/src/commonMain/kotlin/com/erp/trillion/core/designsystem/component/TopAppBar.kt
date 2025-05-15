package com.erp.trillion.core.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.erp.trillion.core.designsystem.theme.TrillionTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    navigationIcon: ImageVector? = null,
    onNavigationClick: () -> Unit = {},
    title: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color(0xFFEEEEEE),
            ),
        title = title,
        navigationIcon = {
            if (navigationIcon != null) {
                IconButton(onNavigationClick) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = null,
                    )
                }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TrillionTheme.color.surface,
            navigationIconContentColor = TrillionTheme.color.heading,
        )
    )
}
