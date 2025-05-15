package com.erp.trillion.feature.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.erp.trillion.core.base.circuit.showInDialog
import com.erp.trillion.core.base.circuit.wrapEventSink
import com.erp.trillion.core.domain.model.FabricRoll
import com.erp.trillion.core.domain.repository.SearchRepository
import com.erp.trillion.core.navigation.ReleaseHistoryScreen
import com.erp.trillion.core.navigation.RollFormScreen
import com.erp.trillion.core.navigation.SearchScreen
import com.erp.trillion.core.ui.model.TableItem
import com.erp.trillion.core.ui.model.toTableItem
import com.slack.circuit.overlay.LocalOverlayHost
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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
        val overlayHost = LocalOverlayHost.current

        var isLoading by rememberRetained { mutableStateOf(false) }
        var searchQuery by rememberRetained { mutableStateOf("") }

        val searchResult: com.erp.trillion.core.ui.model.RollTable by rememberRetained {
            snapshotFlow { searchQuery }
                .distinctUntilChanged()
                .debounce(300L)
                .onEach { isLoading = true }
                .flatMapLatest { query -> searchRepository.searchRoll(query) }
                .map { results ->
                    com.erp.trillion.core.ui.model.RollTable(items = results.map(FabricRoll::toTableItem))
                }
                .onEach { isLoading = false }
        }.collectAsRetainedState(com.erp.trillion.core.ui.model.RollTable())

        val eventSink: CoroutineScope.(SearchEvent) -> Unit = { event ->
            when (event) {
                is SearchEvent.UpdateSearchQuery -> searchQuery = event.query
                is SearchEvent.ShowRollDetail -> {
                    navigator.goTo(ReleaseHistoryScreen(event.item.id))
                }

                is SearchEvent.EditRoll -> {
                    launch {
                        overlayHost.showInDialog(
                            RollFormScreen(event.id),
                            navigator::goTo
                        )
                    }
                }

                is SearchEvent.DeleteRoll -> {
                    navigator.goTo(ReleaseHistoryScreen(event.id))
                }
            }
        }

        return SearchUiState(
            isLoading = isLoading,
            searchQuery = searchQuery,
            searchResults = searchResult,
            eventSink = wrapEventSink(eventSink),
        )
    }
}

data class SearchUiState(
    val isLoading: Boolean,
    val searchQuery: String,
    val searchResults: com.erp.trillion.core.ui.model.RollTable,
    val eventSink: (SearchEvent) -> Unit = {},
) : CircuitUiState

sealed interface SearchEvent : CircuitUiEvent {
    data class UpdateSearchQuery(val query: String) : SearchEvent
    data class ShowRollDetail(val item: TableItem) : SearchEvent
    data class EditRoll(val id: Long) : SearchEvent
    data class DeleteRoll(val id: Long) : SearchEvent
}
