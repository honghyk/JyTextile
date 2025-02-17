package com.erp.jytextile.core.base.circuit

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.slack.circuit.foundation.CircuitContent
import com.slack.circuit.foundation.NavEvent
import com.slack.circuit.overlay.Overlay
import com.slack.circuit.overlay.OverlayHost
import com.slack.circuit.overlay.OverlayNavigator
import com.slack.circuit.runtime.screen.Screen

// copied from https://github.com/slackhq/circuit/blob/main/circuitx/overlays/src/commonMain/kotlin/com/slack/circuitx/overlays/BasicDialogOverlay.kt
class DialogOverlay<Model : Any, Result : Any>(
    private val model: Model,
    private val onDismissRequest: () -> Result,
    private val properties: DialogProperties = DialogProperties(),
    private val content: @Composable (Model, OverlayNavigator<Result>) -> Unit,
) : Overlay<Result> {
    @Composable
    override fun Content(navigator: OverlayNavigator<Result>) {
        Dialog(
            content = {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Transparent,
                    tonalElevation = 0.dp,
                ) {
                    content(model, navigator::finish)
                }
            },
            properties = properties,
            onDismissRequest = {
                // This is apparently as close as we can get to an "onDismiss" callback, which
                // unfortunately has no animation
                navigator.finish(onDismissRequest())
            },
        )
    }
}

suspend fun OverlayHost.showInDialog(
    screen: Screen,
    onGoToScreen: (Screen) -> Unit,
): Unit = show(
    DialogOverlay(model = Unit, onDismissRequest = {}) { _, navigator ->
        CircuitContent(
            screen = screen,
            onNavEvent = { event ->
                when (event) {
                    is NavEvent.Pop -> navigator.finish(Unit)
                    is NavEvent.GoTo -> onGoToScreen(event.screen)
                    else -> Unit
                }
            },
        )
    },
)
