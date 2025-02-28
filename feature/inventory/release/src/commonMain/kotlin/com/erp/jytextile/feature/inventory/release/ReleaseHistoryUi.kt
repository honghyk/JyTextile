package com.erp.jytextile.feature.inventory.release

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.erp.jytextile.core.designsystem.component.ColumnWidth
import com.erp.jytextile.core.designsystem.component.LoadingContent
import com.erp.jytextile.core.designsystem.component.PanelSurface
import com.erp.jytextile.core.designsystem.component.ScrollableTable
import com.erp.jytextile.core.designsystem.component.TopAppBar
import com.erp.jytextile.core.designsystem.icon.JyIcons
import com.erp.jytextile.core.designsystem.theme.Dimension
import com.erp.jytextile.core.navigation.ReleaseHistoryScreen
import com.erp.jytextile.core.navigation.RollDetailScreen
import com.erp.jytextile.core.ui.model.ReleaseHistoryTable
import com.erp.jytextile.core.ui.model.TableItem
import com.slack.circuit.foundation.CircuitContent
import com.slack.circuit.foundation.NavEvent
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject
import org.jetbrains.compose.resources.vectorResource

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
            onNavigationClick = { state.eventSink(ReleaseHistoryEvent.NavigateUp) },
        )
        Column(
            modifier = modifier
                .weight(1f)
                .padding(Dimension.backgroundPadding),
        ) {
            when (state) {
                is ReleaseHistoryUiState.Loading -> {
                    PanelSurface {
                        LoadingContent(modifier = Modifier.fillMaxSize())
                    }
                }

                is ReleaseHistoryUiState.ReleaseHistories -> {
                    CircuitContent(
                        screen = RollDetailScreen(rollId = state.rollId),
                        onNavEvent = { event ->
                            when (event) {
                                is NavEvent.Pop -> state.eventSink(ReleaseHistoryEvent.NavigateUp)
                                else -> Unit
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(Dimension.panelSpacing))
                    ReleaseHistories(
                        modifier = Modifier.weight(1f),
                        table = state.releaseHistoryTable,
                        onDeleteClick = { state.eventSink(ReleaseHistoryEvent.DeleteHistory(it)) },
                    )
                }
            }
        }
    }
}

@Composable
private fun ReleaseHistories(
    table: ReleaseHistoryTable,
    onDeleteClick: (TableItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val columnCount by remember(table.headers.size) { mutableStateOf(table.headers.size + 1) }

    ScrollableTable(
        modifier = modifier,
        title = "출고 이력",
        columnWidths = List(columnCount) { column -> ColumnWidth.MaxIntrinsicWidth },
        rows = table.items,
        headerRowContent = { column ->
            if (column == columnCount - 1) { // delete action row
                HeaderCell(
                    modifier = Modifier,
                    header = ""
                )
            } else {
                HeaderCell(
                    modifier = Modifier,
                    header = table.headers[column]
                )
            }
        },
        rowContent = { item, column ->
            when (column) {
                columnCount - 1 -> { // delete action
                    IconButtonsCell(modifier = Modifier) {
                        this@ScrollableTable.IconButton(
                            modifier = Modifier,
                            icon = vectorResource(JyIcons.Delete),
                            onClick = { onDeleteClick(item) }
                        )
                    }
                }

                else -> {
                    TextCell(
                        modifier = Modifier,
                        text = item.tableRow[column]
                    )
                }
            }
        },
    )
}
