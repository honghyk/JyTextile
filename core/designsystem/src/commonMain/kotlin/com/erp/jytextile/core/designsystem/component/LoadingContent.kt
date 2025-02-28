package com.erp.jytextile.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.theme.JyTheme

@Composable
fun LoadingContent(
    modifier: Modifier = Modifier,
    text: String = "",
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(32.dp),
                color = JyTheme.color.surfaceDim,
                trackColor = JyTheme.color.primary,
            )
            if (text.isNotEmpty()) {
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    style = JyTheme.typography.textLarge,
                    text = text,
                )
            }
        }
    }
}
