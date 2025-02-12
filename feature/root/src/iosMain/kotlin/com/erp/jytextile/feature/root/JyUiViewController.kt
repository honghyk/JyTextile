package com.erp.jytextile.feature.root

import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import me.tatarka.inject.annotations.Inject
import platform.UIKit.UIViewController

typealias JyUiViewController = () -> UIViewController

@Inject
fun JyUiViewController(
    appContent: AppContent
): UIViewController = ComposeUIViewController {

    appContent.Content(
        modifier = Modifier,
    )
}
