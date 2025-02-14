package com.erp.jytextile.feature.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.Snapshot
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.slack.circuit.backstack.SaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.popUntil
import com.slack.circuit.runtime.screen.Screen

@Composable
fun rememberAppState(
    backStack: SaveableBackStack,
    navController: NavHostController = rememberNavController(),
    navigator: Navigator = rememberCircuitNavigator(backStack) { /* no-op */ },
): AppState {
    return remember(backStack, navigator) {
        AppState(
            navController = navController,
            navigator = navigator,
            backStack = backStack,
        )
    }
}

@Stable
class AppState(
    val navController: NavHostController,
    val navigator: Navigator,
    val backStack: SaveableBackStack,
) {

//    private val previousDestination = mutableStateOf<NavDestination?>(null)
//
//    val currentDestination: NavDestination?
//        @Composable get() {
//            // Collect the currentBackStackEntryFlow as a state
//            val currentEntry = navController.currentBackStackEntryFlow
//                .collectAsState(initial = null)
//
//            // Fallback to previousDestination if currentEntry is null
//            return currentEntry.value?.destination.also { destination ->
//                if (destination != null) {
//                    previousDestination.value = destination
//                }
//            } ?: previousDestination.value
//        }

    @Composable
    fun rememberCurrentScreen(): State<Screen> {
        return remember { derivedStateOf { backStack.last().screen } }
    }

//    val currentTopLevelDestination: TopLevelDestination?
//        @Composable get() {
//            return TopLevelDestination.entries.firstOrNull { topLevelDestination ->
//                currentDestination?.hasRoute(route = topLevelDestination.route) == true
//            }
//        }

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val backStack = navigator.peekBackStack()
        if (backStack.lastOrNull() == topLevelDestination.screen) {
            Snapshot.withMutableSnapshot {
                navigator.popUntil { navigator.peekBackStack().size == 1 }
            }
        } else {
            navigator.resetRoot(topLevelDestination.screen, saveState = true, restoreState = true)
        }

//        val topLevelNavOptions = navOptions {
//            // Pop up to the start destination of the graph to
//            // avoid building up a large stack of destinations
//            // on the back stack as users select items
//            popUpTo(navController.graph.findStartDestination().navigatorName) {
//                saveState = true
//            }
//            // Avoid multiple copies of the same destination when
//            // reselecting the same item
//            launchSingleTop = true
//            // Restore state when reselecting a previously selected item
//            restoreState = true
//        }
//
//        when (topLevelDestination) {
//            TopLevelDestination.Inventory -> navController.navigateToInventory(topLevelNavOptions)
//        }
    }
}
