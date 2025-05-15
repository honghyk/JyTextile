package com.erp.trillion.core.designsystem.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erp.trillion.core.designsystem.theme.TrillionTheme

@Composable
fun PanelSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = TrillionTheme.color.surface,
        contentColor = TrillionTheme.color.body,
        content = content,
    )
}
