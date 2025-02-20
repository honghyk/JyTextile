package com.erp.jytextile.feature.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.erp.jytextile.core.domain.model.FabricRoll
import com.erp.jytextile.core.domain.repository.SearchRepository
import com.erp.jytextile.core.navigation.ReleaseHistoryScreen
import com.erp.jytextile.core.navigation.SearchScreen
import com.erp.jytextile.core.ui.model.RollTable
import com.erp.jytextile.core.ui.model.TableItem
import com.erp.jytextile.core.ui.model.toTableItem
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
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
        var searchResult: RollTable? by rememberRetained { mutableStateOf(null) }

        var currentPage by rememberRetained { mutableStateOf(0) }

        LaunchedEffect(searchQuery, currentPage) {
            delay(300L)

            isLoading = true
            val results = async { searchRepository.searchRoll(searchQuery, PAGE_SIZE, currentPage) }
            val pages = async { searchRepository.getSearchResultPageCount(searchQuery, PAGE_SIZE) }
            try {
                searchResult = RollTable(
                    items = results.await().map(FabricRoll::toTableItem),
                    currentPage = currentPage,
                    totalPage = pages.await()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            isLoading = false
        }

        LaunchedEffect(searchQuery) {
            delay(300L)
            currentPage = 0
        }

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
    val searchResults: RollTable?,
    val eventSink: (SearchEvent) -> Unit = {},
) : CircuitUiState

sealed interface SearchEvent : CircuitUiEvent {
    data class UpdateSearchQuery(val query: String) : SearchEvent
    data class RollSelected(val item: TableItem) : SearchEvent
    data object PreviousPage : SearchEvent
    data object NextPage : SearchEvent
}
