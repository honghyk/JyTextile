package com.erp.trillion.feature.root

import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.erp.trillion.core.navigation.ZoneInventoryScreen
import com.slack.circuit.backstack.rememberSaveableBackStack
import me.tatarka.inject.annotations.Inject
import platform.UIKit.UIViewController

typealias TrillionUiViewController = () -> UIViewController

@Inject
fun TrillionUiViewController(
    appContent: AppContent
): UIViewController = ComposeUIViewController {

    val appState = rememberAppState(
        backStack = rememberSaveableBackStack(ZoneInventoryScreen)
    )
    appContent.Content(
        appState = appState,
        modifier = Modifier,
    )
}
