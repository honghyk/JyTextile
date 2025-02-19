package com.erp.jytextile.feature.inventory.release

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.component.JyTopAppBar
import com.erp.jytextile.core.navigation.ReleaseHistoryScreen
import com.erp.jytextile.core.navigation.RollDetailScreen
import com.erp.jytextile.core.ui.TablePanel
import com.erp.jytextile.core.ui.model.ReleaseHistoryTable
import com.slack.circuit.foundation.CircuitContent
import com.slack.circuit.foundation.NavEvent
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject

@Inject
class ReleaseHistoryUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
        is ReleaseHistoryScreen -> {
            ui<ReleaseHistoryUiState> { state, modifier ->
                ReleaseHistoryUi(state, modifier)
            }
        }

        else -> null
    }
}


@Composable
private fun ReleaseHistoryUi(
    state: ReleaseHistoryUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        JyTopAppBar(
            onBackClick = { state.eventSink(ReleaseHistoryEvent.Back) },
            title = { }
        )
        Column(
            modifier = modifier
                .weight(1f)
                .padding(horizontal = 32.dp, vertical = 22.dp),
        ) {
            when (state) {
                is ReleaseHistoryUiState.Loading -> { /* TODO */
                }

                is ReleaseHistoryUiState.ReleaseHistories -> {
                    CircuitContent(
                        screen = RollDetailScreen(rollId = state.rollId),
                        onNavEvent = {  event ->
                            when (event) {
                                is NavEvent.Pop -> state.eventSink(ReleaseHistoryEvent.Back)
                                else -> Unit
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(22.dp))
                    ReleaseHistories(
                        modifier = Modifier.weight(1f),
                        table = state.releaseHistoryTable,
                        currentPage = state.currentPage,
                        totalPage = state.totalPage,
                        onPreviousClick = { state.eventSink(ReleaseHistoryEvent.PreviousPage) },
                        onNextClick = { state.eventSink(ReleaseHistoryEvent.NextPage) },
                        onRemoveClick = { state.eventSink(ReleaseHistoryEvent.Remove) },
                    )
                }
            }
        }
    }
}

@Composable
private fun ReleaseHistories(
    table: ReleaseHistoryTable,
    currentPage: Int,
    totalPage: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        TablePanel(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            title = "출고 이력",
            table = table,
            currentPage = currentPage,
            totalPage = totalPage,
            onItemClick = {},
            onPreviousClick = onPreviousClick,
            onNextClick = onNextClick,
        )
    }
}
