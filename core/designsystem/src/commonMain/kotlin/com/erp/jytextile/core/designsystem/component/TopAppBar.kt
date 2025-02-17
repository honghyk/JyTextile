package com.erp.jytextile.core.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.theme.JyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JyTopAppBar(
    onBackClick: () -> Unit,
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        modifier = modifier
            .padding(start = 2.dp)
            .border(
                width = 1.dp,
                color = Color(0xFFEEEEEE),
            ),
        title = title,
        navigationIcon = {
            Icon(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .clickable(onClick = onBackClick),
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = null
            )
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = JyTheme.color.surface,
            navigationIconContentColor = JyTheme.color.heading,
        )
    )
}
