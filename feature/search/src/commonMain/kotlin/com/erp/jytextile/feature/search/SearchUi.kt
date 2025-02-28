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
import com.erp.jytextile.core.designsystem.component.EmptyContent
import com.erp.jytextile.core.designsystem.component.OutlinedTextField
import com.erp.jytextile.core.designsystem.component.PanelSurface
import com.erp.jytextile.core.designsystem.component.TopAppBar
import com.erp.jytextile.core.designsystem.icon.JyIcons
import com.erp.jytextile.core.designsystem.theme.Dimension
import com.erp.jytextile.core.navigation.SearchScreen
import com.erp.jytextile.core.ui.TablePanel
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
        isLoading = state.isLoading,
        searchQuery = state.searchQuery,
        searchResults = state.searchResults,
        onSearchQueryChanged = { state.eventSink(SearchEvent.UpdateSearchQuery(it)) },
        onRollSelected = { state.eventSink(SearchEvent.RollSelected(it)) },
        onNextPageClick = { state.eventSink(SearchEvent.NextPage) },
        onPreviousPageClick = { state.eventSink(SearchEvent.PreviousPage) },
    )
}

@Composable
private fun SearchUi(
    isLoading: Boolean,
    searchQuery: String,
    searchResults: RollTable?,
    onSearchQueryChanged: (String) -> Unit,
    onRollSelected: (TableItem) -> Unit,
    onNextPageClick: () -> Unit,
    onPreviousPageClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        SearchTopAppBar(
            searchQuery = searchQuery,
            onSearchQueryChanged = onSearchQueryChanged,
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(Dimension.backgroundPadding)
        ) {
            when {
                isLoading || searchResults == null -> {
                    PanelSurface(
                        content = { }
                    )
                }

                searchResults.items.isEmpty() -> {
                    PanelSurface {
                        EmptyContent(
                            modifier = Modifier.fillMaxSize(),
                            image = vectorResource(JyIcons.SearchEmpty),
                            text = "\"검색 결과가 없습니다.\"",
                        )
                    }
                }

                else -> {
                    TablePanel(
                        modifier = Modifier.fillMaxSize(),
                        table = searchResults,
                        onItemClick = onRollSelected,
                        onPreviousClick = onPreviousPageClick,
                        onNextClick = onNextPageClick,
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
