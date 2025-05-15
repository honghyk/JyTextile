package com.erp.trillion.feature.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.erp.trillion.core.designsystem.theme.TrillionTheme
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import me.tatarka.inject.annotations.Inject

interface AppContent {

    @Composable
    fun Content(
        appState: AppState,
        modifier: Modifier,
    )
}

@Inject
class DefaultAppContent(
    private val circuit: Circuit,
) : AppContent {

    @Composable
    override fun Content(
        appState: AppState,
        modifier: Modifier,
    ) {
        CircuitCompositionLocals(circuit) {
            TrillionTheme {
                App(
                    modifier = modifier,
                    appState = appState,
                )
            }
        }
    }
}
