package com.erp.trillion.feature.inventory.roll

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.erp.trillion.core.designsystem.component.ColumnWidth
import com.erp.trillion.core.designsystem.component.LoadingContent
import com.erp.trillion.core.designsystem.component.PaginatedScrollableTable
import com.erp.trillion.core.designsystem.component.PanelSurface
import com.erp.trillion.core.designsystem.component.TopAppBar
import com.erp.trillion.core.designsystem.icon.TrillionIcons
import com.erp.trillion.core.designsystem.theme.Dimension
import com.erp.trillion.core.navigation.RollInventoryScreen
import com.erp.trillion.core.ui.model.TableItem
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject
import org.jetbrains.compose.resources.vectorResource

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
            navigationIcon = TrillionIcons.ArrowBack,
            onNavigationClick = { state.eventSink(RollInventoryEvent.NavigateUp) },
        )
        Box(
            modifier = modifier
                .weight(1f)
                .padding(Dimension.backgroundPadding),
        ) {
            when (state) {
                is RollInventoryUiState.Loading -> {
                    PanelSurface {
                        LoadingContent(modifier = Modifier.fillMaxSize())
                    }
                }

                is RollInventoryUiState.Rolls -> {
                    RollInventoryPanel(
                        modifier = Modifier.fillMaxSize(),
                        table = state.rollTable,
                        onItemClick = { state.eventSink(RollInventoryEvent.ShowRollDetail(it.id)) },
                        onEditClick = { state.eventSink(RollInventoryEvent.EditRoll(it.id)) },
                        onDeleteClick = { state.eventSink(RollInventoryEvent.DeleteRoll(it.id)) },
                        onPreviousClick = { state.eventSink(RollInventoryEvent.ShowPreviousPage) },
                        onNextClick = { state.eventSink(RollInventoryEvent.ShowNextPage) },
                    )
                }
            }
        }
    }
}

@Composable
private fun RollInventoryPanel(
    table: com.erp.trillion.core.ui.model.RollTable,
    onItemClick: (TableItem) -> Unit,
    onEditClick: (TableItem) -> Unit,
    onDeleteClick: (TableItem) -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    PaginatedScrollableTable(
        modifier = modifier,
        title = "Rolls",
        columnWidths = List(table.headers.size) { ColumnWidth.MaxIntrinsicWidth },
        rows = table.items,
        onRowClick = { onItemClick(it) },
        onNext = onNextClick,
        onPrevious = onPreviousClick,
        headerRowContent = { column ->
            HeaderCell(
                modifier = Modifier,
                header = table.headers[column]
            )
        },
        rowContent = { item, column ->
            when (column) {
                0 -> {
                    PrimaryTextCell(
                        modifier = Modifier,
                        text = item.tableRow[column]
                    )
                }

                table.headers.size - 1 -> {
                    IconButtonsCell(modifier = Modifier) {
                        this@PaginatedScrollableTable.IconButton(
                            modifier = Modifier,
                            icon = vectorResource(TrillionIcons.Edit),
                            onClick = { onEditClick(item) }
                        )
                        this@PaginatedScrollableTable.IconButton(
                            modifier = Modifier,
                            icon = vectorResource(TrillionIcons.Delete),
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
