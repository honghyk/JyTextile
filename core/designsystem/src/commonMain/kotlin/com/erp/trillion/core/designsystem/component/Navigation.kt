package com.erp.trillion.core.designsystem.component

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
import com.erp.trillion.core.designsystem.theme.Palette
import com.erp.trillion.core.designsystem.theme.TrillionTheme

@Composable
private fun TrillionNavigationBar(
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
fun TrillionNavigationSuiteScaffold(
    navigationSuiteItems: TrillionNavigationSuiteScope.() -> Unit,
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
                TrillionNavigationSuite(
                    layoutType = layoutType,
                    colors = TrillionNavigationSuiteDefaults.colors(),
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

object TrillionNavigationDefaults {
    @Composable
    fun navigationContainerColor() = TrillionTheme.color.surface

    @Composable
    fun navigationContentColor() = TrillionTheme.color.subHeading

    @Composable
    fun navigationSelectedItemColor() = TrillionTheme.color.primary

    @Composable
    fun navigationIndicatorColor() = Palette.grey50
}

@Composable
private fun TrillionNavigationSuite(
    modifier: Modifier = Modifier,
    layoutType: NavigationSuiteType =
        NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(WindowAdaptiveInfoDefault),
    colors: NavigationSuiteColors = NavigationSuiteDefaults.colors(),
    content: TrillionNavigationSuiteScope.() -> Unit
) {
    val scope by rememberStateOfItems(content)
    val itemColors = TrillionNavigationSuiteDefaults.itemColors()

    when (layoutType) {
        NavigationSuiteType.NavigationBar -> {
            TrillionNavigationBar(
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
            TrillionNavigationRail(
                containerColor = colors.navigationRailContainerColor,
                contentColor = colors.navigationRailContentColor,
            ) {
                scope.itemList.forEach {
                    TrillionNavigationRailItem(
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
            TrillionDrawerSheet(
                modifier = modifier,
                drawerContainerColor = colors.navigationDrawerContainerColor,
                drawerContentColor = colors.navigationDrawerContentColor
            ) {
                scope.itemList.forEach {
                    TrillionNavigationDrawerItem(
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

sealed interface TrillionNavigationSuiteScope {

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

object TrillionNavigationSuiteDefaults {

    @Composable
    fun colors(): NavigationSuiteColors = NavigationSuiteDefaults.colors(
        navigationBarContainerColor = TrillionNavigationDefaults.navigationContainerColor(),
        navigationBarContentColor = TrillionNavigationDefaults.navigationContentColor(),
        navigationRailContainerColor = TrillionNavigationDefaults.navigationContainerColor(),
        navigationRailContentColor = TrillionNavigationDefaults.navigationContentColor(),
        navigationDrawerContainerColor = TrillionNavigationDefaults.navigationContainerColor(),
        navigationDrawerContentColor = TrillionNavigationDefaults.navigationContentColor(),
    )

    @Composable
    fun itemColors(): NavigationSuiteItemColors =
        NavigationSuiteItemColors(
            navigationBarItemColors = NavigationBarItemDefaults.colors(
                selectedIconColor = TrillionNavigationDefaults.navigationSelectedItemColor(),
                unselectedIconColor = TrillionNavigationDefaults.navigationContentColor(),
                selectedTextColor = TrillionNavigationDefaults.navigationSelectedItemColor(),
                unselectedTextColor = TrillionNavigationDefaults.navigationContentColor(),
                indicatorColor = TrillionNavigationDefaults.navigationIndicatorColor(),
            ),
            navigationRailItemColors = NavigationRailItemDefaults.colors(
                selectedIconColor = TrillionNavigationDefaults.navigationSelectedItemColor(),
                unselectedIconColor = TrillionNavigationDefaults.navigationContentColor(),
                selectedTextColor = TrillionNavigationDefaults.navigationSelectedItemColor(),
                unselectedTextColor = TrillionNavigationDefaults.navigationContentColor(),
                indicatorColor = TrillionNavigationDefaults.navigationIndicatorColor(),
            ),
            navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
                selectedContainerColor = Color.Transparent,
                unselectedContainerColor = Color.Transparent,
                selectedIconColor = TrillionNavigationDefaults.navigationSelectedItemColor(),
                unselectedIconColor = TrillionNavigationDefaults.navigationContentColor(),
                selectedTextColor = TrillionNavigationDefaults.navigationSelectedItemColor(),
                unselectedTextColor = TrillionNavigationDefaults.navigationContentColor(),
            ),
        )
}

private class TrillionNavigationSuiteScopeImpl : TrillionNavigationSuiteScope, NavigationSuiteItemProvider {

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
    content: TrillionNavigationSuiteScope.() -> Unit
): State<NavigationSuiteItemProvider> {
    val latestContent = rememberUpdatedState(content)
    return remember {
        derivedStateOf { TrillionNavigationSuiteScopeImpl().apply(latestContent.value) }
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
