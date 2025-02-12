package com.erp.jytextile

import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.erp.jytextile.shared.inject.DesktopApplicationComponent
import com.erp.jytextile.shared.inject.WindowComponent
import com.erp.jytextile.shared.inject.create
import java.awt.Dimension

fun main() = application {
    val applicationComponent = remember {
        DesktopApplicationComponent.create()
    }

    Window(
        title = "TextileERP",
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(800, 600)
        val component = remember(applicationComponent) {
            WindowComponent.create(applicationComponent)
        }
        component.appContent.Content(
            modifier = Modifier,
        )
    }
}
