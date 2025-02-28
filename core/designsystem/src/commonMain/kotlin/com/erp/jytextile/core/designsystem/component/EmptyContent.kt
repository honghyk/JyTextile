package com.erp.jytextile.core.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.theme.JyTheme

@Composable
fun EmptyContent(
    image: ImageVector,
    modifier: Modifier = Modifier,
    text: String = "",
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier.size(120.dp),
                imageVector = image,
                contentDescription = null,
            )
            if (text.isNotEmpty()) {
                Text(
                    style = JyTheme.typography.textXLarge,
                    text = text,
                )
            }
        }
    }
}
