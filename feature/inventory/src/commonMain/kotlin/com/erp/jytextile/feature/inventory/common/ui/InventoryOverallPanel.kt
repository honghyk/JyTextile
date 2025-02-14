package com.erp.jytextile.feature.inventory.common.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.component.PanelSurface
import com.erp.jytextile.core.designsystem.theme.JyTheme

@Composable
internal fun InventoryOverallPanel(
    title: String,
    total: Int,
    modifier: Modifier = Modifier
) {
    PanelSurface(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = 20.dp,
                    bottom = 24.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
        ) {
            Text(
                style = JyTheme.typography.textXLarge,
                color = JyTheme.color.heading,
                text = title,
            )
            Spacer(modifier = Modifier.height(22.dp))
            Row(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(54.dp),
            ) {
                OverallItem(
                    titleColor = JyTheme.color.primary,
                    title = "Total",
                    value = total.toString(),
                )
            }
        }
    }
}

@Composable
private fun OverallItem(
    titleColor: Color,
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            style = JyTheme.typography.textMedium,
            color = titleColor,
            fontWeight = FontWeight.SemiBold,
            text = title,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            style = JyTheme.typography.textMedium,
            color = JyTheme.color.subHeading,
            text = value,
        )
    }
}
