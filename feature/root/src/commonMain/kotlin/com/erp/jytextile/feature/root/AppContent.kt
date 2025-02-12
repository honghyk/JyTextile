package com.erp.jytextile.feature.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.erp.jytextile.shared.base.route.AppRouteFactory
import com.erp.jytextile.shared.designsystem.theme.JyTheme
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
        JyTheme {
            JyApp(
                modifier = modifier,
                routeFactories = routeFactories,
            )
        }
    }
}
