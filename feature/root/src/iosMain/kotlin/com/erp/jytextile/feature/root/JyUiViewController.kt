package com.erp.jytextile.feature.root

import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.erp.jytextile.feature.inventory.navigation.InventoryScreen
import com.slack.circuit.backstack.rememberSaveableBackStack
import me.tatarka.inject.annotations.Inject
import platform.UIKit.UIViewController

typealias JyUiViewController = () -> UIViewController

@Inject
fun JyUiViewController(
    appContent: AppContent
): UIViewController = ComposeUIViewController {

    val appState = rememberAppState(
        backStack = rememberSaveableBackStack(InventoryScreen)
    )
    appContent.Content(
        appState = appState,
        modifier = Modifier,
    )
}
