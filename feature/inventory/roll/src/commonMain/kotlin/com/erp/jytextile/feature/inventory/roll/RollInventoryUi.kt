package com.erp.jytextile.feature.inventory.roll

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.component.JyTopAppBar
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
        JyTopAppBar(
            onBackClick = { state.eventSink(RollInventoryEvent.Back) },
            title = {}
        )
        Box(
            modifier = modifier
                .weight(1f)
                .padding(
                    horizontal = 32.dp,
                    vertical = 22.dp,
                ),
        ) {
            when (state) {
                is RollInventoryUiState.Loading -> { /* TODO */
                }

                is RollInventoryUiState.Rolls -> {
                    RollInventory(
                        table = state.rollTable,
                        currentPage = state.currentPage,
                        totalPage = state.totalPage,
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
private fun RollInventory(
    table: RollTable,
    currentPage: Int,
    totalPage: Int,
    onItemClick: (Long) -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        TablePanel(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            title = "Rolls",
            table = table,
            currentPage = currentPage,
            totalPage = totalPage,
            onItemClick = { onItemClick(it.id) },
            onPreviousClick = onPreviousClick,
            onNextClick = onNextClick,
        )
    }
}
