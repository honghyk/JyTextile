package com.erp.jytextile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.erp.jytextile.feature.inventory.InventoryScreen
import com.erp.jytextile.shared.designsystem.theme.JyTheme
import java.awt.Dimension

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TextileERP",
    ) {
        window.minimumSize = Dimension(800, 600)
        JyTheme {
            InventoryScreen(
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
