package com.erp.trillion.feature.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.Snapshot
import com.slack.circuit.backstack.SaveableBackStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.popUntil
import com.slack.circuit.runtime.screen.Screen

@Composable
fun rememberAppState(
    backStack: SaveableBackStack,
    navigator: Navigator = rememberCircuitNavigator(backStack) { /* no-op */ },
): AppState {
    return remember(backStack, navigator) {
        AppState(
            navigator = navigator,
            backStack = backStack,
        )
    }
}

@Stable
class AppState(
    val navigator: Navigator,
    val backStack: SaveableBackStack,
) {

    @Composable
    fun rememberCurrentScreen(): State<Screen> {
        return remember { derivedStateOf { backStack.last().screen } }
    }

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
    }
}
