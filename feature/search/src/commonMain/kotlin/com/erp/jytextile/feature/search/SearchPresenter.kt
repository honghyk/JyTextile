package com.erp.jytextile.feature.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.core.domain.repository.SearchRepository
import com.erp.jytextile.core.navigation.ReleaseHistoryScreen
import com.erp.jytextile.core.navigation.SearchScreen
import com.erp.jytextile.core.ui.model.RollTable
import com.erp.jytextile.core.ui.model.TableItem
import com.erp.jytextile.core.ui.model.toTableItem
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class SearchPresenterFactory(
    private val presenterFactory: (Navigator) -> SearchPresenter
) : Presenter.Factory {

    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext
    ): Presenter<*>? {
        return when (screen) {
            is SearchScreen -> presenterFactory(navigator)
            else -> return null
        }
    }
}

@Inject
class SearchPresenter(
    @Assisted val navigator: Navigator,
    private val searchRepository: SearchRepository,
) : Presenter<SearchUiState> {

    @Composable
    override fun present(): SearchUiState {
        var isLoading by rememberRetained { mutableStateOf(false) }
        var searchQuery by rememberRetained { mutableStateOf("") }

        var currentPage by rememberRetained { mutableStateOf(0) }

        val searchResult: RollTable by rememberRetained {
            snapshotFlow { searchQuery }
                .distinctUntilChanged()
                .debounce(300L)
                .onEach { isLoading = true }
                .flatMapLatest { query ->
                    searchRepository.searchRoll(query, PAGE_SIZE, 0)
                }
                .map { results ->
                    RollTable(
                        items = results.map(FabricRoll::toTableItem),
                        currentPage = -1,
                        totalPage = -1,
                    )
                }
                .onEach { isLoading = false }
        }.collectAsRetainedState(RollTable(currentPage = -1, totalPage = -1))

        return SearchUiState(
            isLoading = isLoading,
            searchQuery = searchQuery,
            searchResults = searchResult
        ) { event ->
            when (event) {
                is SearchEvent.UpdateSearchQuery -> searchQuery = event.query
                is SearchEvent.RollSelected -> {
                    navigator.goTo(ReleaseHistoryScreen(event.item.id))
                }

                SearchEvent.NextPage -> {
                    searchResult?.let {
                        currentPage = (currentPage + 1).coerceAtMost(it.totalPage - 1)
                    }
                }

                SearchEvent.PreviousPage -> {
                    currentPage = (currentPage - 1).coerceAtLeast(0)
                }
            }
        }
    }
}

private const val PAGE_SIZE = 20

data class SearchUiState(
    val isLoading: Boolean,
    val searchQuery: String,
    val searchResults: RollTable,
    val eventSink: (SearchEvent) -> Unit = {},
) : CircuitUiState

sealed interface SearchEvent : CircuitUiEvent {
    data class UpdateSearchQuery(val query: String) : SearchEvent
    data class RollSelected(val item: TableItem) : SearchEvent
    data object PreviousPage : SearchEvent
    data object NextPage : SearchEvent
}
