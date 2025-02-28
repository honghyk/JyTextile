package com.erp.jytextile.feature.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.component.ColumnWidth
import com.erp.jytextile.core.designsystem.component.EmptyContent
import com.erp.jytextile.core.designsystem.component.LoadingContent
import com.erp.jytextile.core.designsystem.component.OutlinedTextField
import com.erp.jytextile.core.designsystem.component.PanelSurface
import com.erp.jytextile.core.designsystem.component.ScrollableTable
import com.erp.jytextile.core.designsystem.component.TopAppBar
import com.erp.jytextile.core.designsystem.icon.JyIcons
import com.erp.jytextile.core.designsystem.theme.Dimension
import com.erp.jytextile.core.navigation.SearchScreen
import com.erp.jytextile.core.ui.model.RollTable
import com.erp.jytextile.core.ui.model.TableItem
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject
import org.jetbrains.compose.resources.vectorResource

@Inject
class SearchUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
        is SearchScreen -> ui<SearchUiState> { state, modifier -> SearchUi(state, modifier) }
        else -> null
    }
}

@Composable
private fun SearchUi(
    state: SearchUiState,
    modifier: Modifier = Modifier,
) {
    SearchUi(
        modifier = modifier,
        state = state,
        onSearchQueryChanged = { state.eventSink(SearchEvent.UpdateSearchQuery(it)) },
        onRollClick = { state.eventSink(SearchEvent.ShowRollDetail(it)) },
    )
}

@Composable
private fun SearchUi(
    state: SearchUiState,
    onSearchQueryChanged: (String) -> Unit,
    onRollClick: (TableItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        SearchTopAppBar(
            searchQuery = state.searchQuery,
            onSearchQueryChanged = onSearchQueryChanged,
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(Dimension.backgroundPadding)
        ) {
            when {
                state.isLoading -> {
                    PanelSurface {
                        LoadingContent(
                            modifier = Modifier.fillMaxSize(),
                            text = "Loading..."
                        )
                    }
                }

                state.searchResults.items.isEmpty() -> {
                    PanelSurface {
                        EmptyContent(
                            modifier = Modifier.fillMaxSize(),
                            image = vectorResource(JyIcons.SearchEmpty),
                            text = "\"검색 결과가 없습니다.\"",
                        )
                    }
                }

                else -> {
                    SearchResultTable(
                        modifier = Modifier.fillMaxSize(),
                        searchResults = state.searchResults,
                        onRollClick = onRollClick,
                        onEditClick = { state.eventSink(SearchEvent.EditRoll(it.id)) },
                        onDeleteClick = { state.eventSink(SearchEvent.DeleteRoll(it.id)) },
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchTopAppBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = {
            SearchTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 32.dp),
                searchQuery = searchQuery,
                onSearchQueryChanged = onSearchQueryChanged,
            )
        },
    )
}

@Composable
private fun SearchTextField(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        modifier = modifier,
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        }
    )
}

@Composable
private fun SearchResultTable(
    searchResults: RollTable,
    onRollClick: (TableItem) -> Unit,
    onEditClick: (TableItem) -> Unit = {},
    onDeleteClick: (TableItem) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    ScrollableTable(
        modifier = modifier,
        title = "검색 결과",
        columnWidths = List(searchResults.headers.size) { ColumnWidth.MaxIntrinsicWidth },
        rows = searchResults.items,
        onRowClick = { onRollClick(it) },
        headerRowContent = { column ->
            HeaderCell(
                modifier = Modifier,
                header = searchResults.headers[column]
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

                searchResults.headers.size - 1 -> {
                    IconButtonsCell(modifier = Modifier) {
                        this@ScrollableTable.IconButton(
                            modifier = Modifier,
                            icon = vectorResource(JyIcons.Edit),
                            onClick = { onEditClick(item) }
                        )
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
