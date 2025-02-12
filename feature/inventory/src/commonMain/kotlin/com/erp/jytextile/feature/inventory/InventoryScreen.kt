package com.erp.jytextile.feature.inventory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erp.jytextile.feature.inventory.component.InventoryOverallPanel
import com.erp.jytextile.feature.inventory.component.InventoryTablePanel
import com.erp.jytextile.shared.designsystem.theme.JyTheme
import kotlinx.serialization.Serializable

@Composable
fun InventoryScreen(
    viewModel: InventoryViewModel,
    modifier: Modifier = Modifier,
) {
    InventoryScreen()
}

@Composable
private fun InventoryScreen(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .padding(
                horizontal = 32.dp,
                vertical = 22.dp,
            ),
        color = JyTheme.color.surfaceDim,
    ) {
        Column(modifier = modifier) {
            InventoryOverallPanel(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                total = 99,
            )
            Spacer(modifier = Modifier.height(22.dp))
            InventoryTablePanel(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}
