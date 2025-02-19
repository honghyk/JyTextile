package com.erp.jytextile

import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.erp.jytextile.core.navigation.ZoneInventoryScreen
import com.erp.jytextile.feature.root.rememberAppState
import com.erp.jytextile.shared.DesktopApplicationComponent
import com.erp.jytextile.shared.WindowComponent
import com.erp.jytextile.shared.create
import com.slack.circuit.backstack.rememberSaveableBackStack
import java.awt.Dimension

fun main() = application {
    val applicationComponent = remember {
        DesktopApplicationComponent.create()
    }

    Window(
        title = "TextileERP",
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(600, 0)
        val component = remember(applicationComponent) {
            WindowComponent.create(applicationComponent)
        }
        val appState = rememberAppState(
            backStack = rememberSaveableBackStack(ZoneInventoryScreen)
        )
        component.appContent.Content(
            appState = appState,
            modifier = Modifier,
        )
    }
}
