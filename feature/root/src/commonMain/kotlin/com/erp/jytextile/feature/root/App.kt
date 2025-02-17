package com.erp.jytextile.feature.root

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.erp.jytextile.core.designsystem.component.JyNavigationSuiteScaffold
import com.erp.jytextile.core.designsystem.theme.JyTheme
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.overlay.ContentWithOverlays
import org.jetbrains.compose.resources.vectorResource

@Composable
fun App(
    appState: AppState,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
) {
    val currentScreen by appState.rememberCurrentScreen()

    JyNavigationSuiteScaffold(
        modifier = modifier,
        navigationSuiteItems = {
            appState.topLevelDestinations.forEach { destination ->
                val selected = currentScreen == destination.screen
                item(
                    selected = selected,
                    onClick = { appState.navigateToTopLevelDestination(destination) },
                    icon = {
                        Icon(
                            imageVector = vectorResource(destination.icon),
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(
                            text = destination.iconText
                        )
                    },
                )
            }
        },
        windowAdaptiveInfo = windowAdaptiveInfo,
    ) {
        Scaffold(
            containerColor = JyTheme.color.surfaceDim,
        ) { padding ->
            ContentWithOverlays(
                modifier = modifier.padding(padding),
            ) {
                NavigableCircuitContent(
                    navigator = appState.navigator,
                    backStack = appState.backStack
                )
            }
        }
    }
}
