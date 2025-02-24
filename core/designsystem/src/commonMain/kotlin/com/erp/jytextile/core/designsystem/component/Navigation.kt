package com.erp.jytextile.core.designsystem.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collection.MutableVector
import androidx.compose.runtime.collection.mutableVectorOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.erp.jytextile.core.designsystem.theme.JyTheme
import com.erp.jytextile.core.designsystem.theme.Palette

@Composable
private fun JyNavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = NavigationBarDefaults.containerColor,
    contentColor: Color = contentColorFor(containerColor),
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        tonalElevation = 0.dp,
        content = content,
    )
}

@Composable
fun JyNavigationSuiteScaffold(
    navigationSuiteItems: JyNavigationSuiteScope.() -> Unit,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    content: @Composable () -> Unit,
) {
    val layoutType = with(windowAdaptiveInfo) {
        when (windowSizeClass.windowWidthSizeClass) {
            WindowWidthSizeClass.MEDIUM,
            WindowWidthSizeClass.EXPANDED -> NavigationSuiteType.NavigationRail

            else -> NavigationSuiteType.None
        }
    }

    Surface(
        modifier = modifier,
        color = Color.Transparent,
    ) {
        NavigationSuiteScaffoldLayout(
            navigationSuite = {
                JyNavigationSuite(
                    layoutType = layoutType,
                    colors = JyNavigationSuiteDefaults.colors(),
                    content = navigationSuiteItems,
                )
            },
            layoutType = layoutType,
            content = {
                Box(
                    Modifier.consumeWindowInsets(
                        when (layoutType) {
                            NavigationSuiteType.NavigationBar ->
                                NavigationBarDefaults.windowInsets

                            NavigationSuiteType.NavigationRail ->
                                NavigationRailDefaults.windowInsets

                            NavigationSuiteType.NavigationDrawer ->
                                DrawerDefaults.windowInsets

                            else -> WindowInsets(0, 0, 0, 0)
                        }
                    )
                ) {
                    content()
                }
            }
        )
    }
}

object JyNavigationDefaults {
    @Composable
    fun navigationContainerColor() = JyTheme.color.surface

    @Composable
    fun navigationContentColor() = JyTheme.color.subHeading

    @Composable
    fun navigationSelectedItemColor() = JyTheme.color.primary

    @Composable
    fun navigationIndicatorColor() = Palette.grey50
}

@Composable
private fun JyNavigationSuite(
    modifier: Modifier = Modifier,
    layoutType: NavigationSuiteType =
        NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(WindowAdaptiveInfoDefault),
    colors: NavigationSuiteColors = NavigationSuiteDefaults.colors(),
    content: JyNavigationSuiteScope.() -> Unit
) {
    val scope by rememberStateOfItems(content)
    val itemColors = JyNavigationSuiteDefaults.itemColors()

    when (layoutType) {
        NavigationSuiteType.NavigationBar -> {
            JyNavigationBar(
                containerColor = colors.navigationBarContainerColor,
                contentColor = colors.navigationBarContentColor,
            ) {
                scope.itemList.forEach {
                    NavigationBarItem(
                        modifier = it.modifier,
                        selected = it.selected,
                        onClick = it.onClick,
                        icon = { it.icon() },
                        enabled = it.enabled,
                        label = it.label,
                        colors = itemColors.navigationBarItemColors,
                        alwaysShowLabel = it.alwaysShowLabel,
                    )
                }
            }
        }

        NavigationSuiteType.NavigationRail -> {
            JyNavigationRail(
                containerColor = colors.navigationRailContainerColor,
                contentColor = colors.navigationRailContentColor,
            ) {
                scope.itemList.forEach {
                    JyNavigationRailItem(
                        modifier = it.modifier,
                        selected = it.selected,
                        onClick = it.onClick,
                        icon = { it.icon() },
                        enabled = it.enabled,
                        colors = itemColors.navigationRailItemColors,
                    )
                }
            }
        }

        NavigationSuiteType.NavigationDrawer -> {
            JyDrawerSheet(
                modifier = modifier,
                drawerContainerColor = colors.navigationDrawerContainerColor,
                drawerContentColor = colors.navigationDrawerContentColor
            ) {
                scope.itemList.forEach {
                    JyNavigationDrawerItem(
                        modifier = it.modifier,
                        selected = it.selected,
                        onClick = it.onClick,
                        icon = it.icon,
                        label = { it.label?.invoke() ?: Text("") },
                        colors = itemColors.navigationDrawerItemColors,
                        interactionSource = it.interactionSource
                    )
                }
            }
        }

        NavigationSuiteType.None -> { /* Do nothing. */
        }
    }
}

sealed interface JyNavigationSuiteScope {

    fun item(
        selected: Boolean,
        onClick: () -> Unit,
        icon: @Composable () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        label: @Composable (() -> Unit)? = null,
        alwaysShowLabel: Boolean = true,
        interactionSource: MutableInteractionSource? = null
    )
}

object JyNavigationSuiteDefaults {

    @Composable
    fun colors(): NavigationSuiteColors = NavigationSuiteDefaults.colors(
        navigationBarContainerColor = JyNavigationDefaults.navigationContainerColor(),
        navigationBarContentColor = JyNavigationDefaults.navigationContentColor(),
        navigationRailContainerColor = JyNavigationDefaults.navigationContainerColor(),
        navigationRailContentColor = JyNavigationDefaults.navigationContentColor(),
        navigationDrawerContainerColor = JyNavigationDefaults.navigationContainerColor(),
        navigationDrawerContentColor = JyNavigationDefaults.navigationContentColor(),
    )

    @Composable
    fun itemColors(): NavigationSuiteItemColors =
        NavigationSuiteItemColors(
            navigationBarItemColors = NavigationBarItemDefaults.colors(
                selectedIconColor = JyNavigationDefaults.navigationSelectedItemColor(),
                unselectedIconColor = JyNavigationDefaults.navigationContentColor(),
                selectedTextColor = JyNavigationDefaults.navigationSelectedItemColor(),
                unselectedTextColor = JyNavigationDefaults.navigationContentColor(),
                indicatorColor = JyNavigationDefaults.navigationIndicatorColor(),
            ),
            navigationRailItemColors = NavigationRailItemDefaults.colors(
                selectedIconColor = JyNavigationDefaults.navigationSelectedItemColor(),
                unselectedIconColor = JyNavigationDefaults.navigationContentColor(),
                selectedTextColor = JyNavigationDefaults.navigationSelectedItemColor(),
                unselectedTextColor = JyNavigationDefaults.navigationContentColor(),
                indicatorColor = JyNavigationDefaults.navigationIndicatorColor(),
            ),
            navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = Color.Transparent,
                unselectedContainerColor = Color.Transparent,
                selectedIconColor = JyNavigationDefaults.navigationSelectedItemColor(),
                unselectedIconColor = JyNavigationDefaults.navigationContentColor(),
                selectedTextColor = JyNavigationDefaults.navigationSelectedItemColor(),
                unselectedTextColor = JyNavigationDefaults.navigationContentColor(),
            ),
        )
}

private class JyNavigationSuiteScopeImpl : JyNavigationSuiteScope, NavigationSuiteItemProvider {

    override fun item(
        selected: Boolean,
        onClick: () -> Unit,
        icon: @Composable () -> Unit,
        modifier: Modifier,
        enabled: Boolean,
        label: @Composable (() -> Unit)?,
        alwaysShowLabel: Boolean,
        interactionSource: MutableInteractionSource?
    ) {
        itemList.add(
            NavigationSuiteItem(
                selected = selected,
                onClick = onClick,
                icon = icon,
                modifier = modifier,
                enabled = enabled,
                label = label,
                alwaysShowLabel = alwaysShowLabel,
                interactionSource = interactionSource
            )
        )
    }

    override val itemList: MutableVector<NavigationSuiteItem> = mutableVectorOf()

    override val itemsCount: Int
        get() = itemList.size
}

internal val WindowAdaptiveInfoDefault
    @Composable
    get() = currentWindowAdaptiveInfo()

@Composable
private fun rememberStateOfItems(
    content: JyNavigationSuiteScope.() -> Unit
): State<NavigationSuiteItemProvider> {
    val latestContent = rememberUpdatedState(content)
    return remember {
        derivedStateOf { JyNavigationSuiteScopeImpl().apply(latestContent.value) }
    }
}

private interface NavigationSuiteItemProvider {
    val itemsCount: Int
    val itemList: MutableVector<NavigationSuiteItem>
}

private class NavigationSuiteItem(
    val selected: Boolean,
    val onClick: () -> Unit,
    val icon: @Composable () -> Unit,
    val modifier: Modifier,
    val enabled: Boolean,
    val label: @Composable (() -> Unit)?,
    val alwaysShowLabel: Boolean,
    val interactionSource: MutableInteractionSource?
)
