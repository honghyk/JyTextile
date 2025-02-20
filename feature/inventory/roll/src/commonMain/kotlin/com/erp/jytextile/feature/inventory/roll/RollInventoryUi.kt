package com.erp.jytextile.feature.inventory.roll

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.erp.jytextile.core.designsystem.component.TopAppBar
import com.erp.jytextile.core.designsystem.icon.JyIcons
import com.erp.jytextile.core.designsystem.theme.Dimension
import com.erp.jytextile.core.navigation.RollInventoryScreen
import com.erp.jytextile.core.ui.TablePanel
import com.erp.jytextile.core.ui.model.RollTable
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject

@Inject
class RollInventoryUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
        is RollInventoryScreen -> {
            ui<RollInventoryUiState> { state, modifier ->
                RollInventoryUi(state, modifier)
            }
        }

        else -> null
    }
}


@Composable
fun RollInventoryUi(
    state: RollInventoryUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TopAppBar(
            navigationIcon = JyIcons.ArrowBack,
            onNavigationClick = { state.eventSink(RollInventoryEvent.Back) },
        )
        Box(
            modifier = modifier
                .weight(1f)
                .padding(Dimension.backgroundPadding),
        ) {
            when (state) {
                is RollInventoryUiState.Loading -> { /* TODO */
                }

                is RollInventoryUiState.Rolls -> {
                    RollInventoryPanel(
                        modifier = Modifier.fillMaxSize(),
                        table = state.rollTable,
                        onItemClick = { state.eventSink(RollInventoryEvent.RollSelected(it)) },
                        onPreviousClick = { state.eventSink(RollInventoryEvent.PreviousPage) },
                        onNextClick = { state.eventSink(RollInventoryEvent.NextPage) },
                    )
                }
            }
        }
    }
}

@Composable
private fun RollInventoryPanel(
    table: RollTable,
    onItemClick: (Long) -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TablePanel(
        modifier = modifier,
        title = "Rolls",
        table = table,
        onItemClick = { onItemClick(it.id) },
        onPreviousClick = onPreviousClick,
        onNextClick = onNextClick,
    )
}
