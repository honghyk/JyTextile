package com.erp.jytextile.feature.inventory.release

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.erp.jytextile.core.designsystem.component.JyOutlinedButton
import com.erp.jytextile.core.designsystem.component.TopAppBar
import com.erp.jytextile.core.designsystem.icon.JyIcons
import com.erp.jytextile.core.designsystem.theme.Dimension
import com.erp.jytextile.core.navigation.ReleaseHistoryScreen
import com.erp.jytextile.core.navigation.RollDetailScreen
import com.erp.jytextile.core.ui.TablePanel
import com.erp.jytextile.core.ui.model.ReleaseHistoryTable
import com.erp.jytextile.core.ui.model.TableItem
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
        TopAppBar(
            navigationIcon = JyIcons.ArrowBack,
            onNavigationClick = { state.eventSink(ReleaseHistoryEvent.Back) },
        )
        Column(
            modifier = modifier
                .weight(1f)
                .padding(Dimension.backgroundPadding),
        ) {
            when (state) {
                is ReleaseHistoryUiState.Loading -> { /* TODO */
                }

                is ReleaseHistoryUiState.ReleaseHistories -> {
                    CircuitContent(
                        screen = RollDetailScreen(rollId = state.rollId),
                        onNavEvent = { event ->
                            when (event) {
                                is NavEvent.Pop -> state.eventSink(ReleaseHistoryEvent.Back)
                                else -> Unit
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(Dimension.panelSpacing))
                    ReleaseHistories(
                        modifier = Modifier.weight(1f),
                        table = state.releaseHistoryTable,
                        selectedRows = state.selectedRows,
                        onItemClick = { state.eventSink(ReleaseHistoryEvent.SelectRow(it)) },
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
    selectedRows: List<TableItem>,
    onItemClick: (TableItem) -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TablePanel(
        modifier = modifier,
        title = "출고 이력",
        table = table,
        selectedRows = selectedRows,
        onItemSelected = { _, item -> onItemClick(item) },
        onItemClick = onItemClick,
        onPreviousClick = {},
        onNextClick = {},
        titleActionButtons = {
            JyOutlinedButton(
                enabled = selectedRows.isNotEmpty(),
                onClick = onRemoveClick,
                content = { Text(maxLines = 1, text = "삭제") }
            )
        }
    )
}
