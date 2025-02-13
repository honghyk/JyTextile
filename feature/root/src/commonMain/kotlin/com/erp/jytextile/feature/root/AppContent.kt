package com.erp.jytextile.feature.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.erp.jytextile.core.base.route.AppRouteFactory
import com.erp.jytextile.core.designsystem.theme.JyTheme
import me.tatarka.inject.annotations.Inject

interface AppContent {

    @Composable
    fun Content(
        modifier: Modifier
    )
}

@Inject
class DefaultAppContent(
    private val routeFactories: Set<AppRouteFactory>,
) : AppContent {

    @Composable
    override fun Content(
        modifier: Modifier
    ) {
        val appState = rememberAppState()

        JyTheme {
            App(
                modifier = modifier,
                appState = appState,
                routeFactories = routeFactories,
            )
        }
    }
}
