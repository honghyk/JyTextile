package com.erp.jytextile.feature.inventory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erp.jytextile.core.domain.model.Section
import com.erp.jytextile.core.domain.repository.InventoryRepository
import com.erp.jytextile.feature.inventory.model.SectionTable
import com.erp.jytextile.feature.inventory.model.SectionTableItem
import com.erp.jytextile.feature.inventory.model.toTableItem
import com.erp.jytextile.feature.inventory.navigation.InventoryScreen
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class SectionInventoryPresenterFactory(
    private val presenterFactory: (Navigator) -> SectionInventoryPresenter,
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext
    ): Presenter<*>? {
        return when (screen) {
            InventoryScreen -> presenterFactory(navigator)
            else -> null
        }
    }
}

@Inject
class SectionInventoryPresenter(
    @Assisted private val navigator: Navigator,
    private val inventoryRepository: InventoryRepository,
) : Presenter<SectionInventoryUiState> {

    @Composable
    override fun present(): SectionInventoryUiState {
        val sectionsCount by inventoryRepository.getSectionsCount().collectAsRetainedState(0)

        var currentPage by rememberRetained { mutableStateOf(0) }
        val sectionPages by inventoryRepository.getSectionPages().collectAsRetainedState(0)

        val sectionTable by rememberRetained(currentPage) {
            inventoryRepository.getSections(currentPage).map { sections ->
                SectionTable(
                    items = sections.map(Section::toTableItem)
                )
            }
        }.collectAsRetainedState(null)

        return when {
            sectionTable == null -> SectionInventoryUiState.Loading

            else -> SectionInventoryUiState.Sections(
                sectionTable = sectionTable!!,
                sectionsCount = sectionsCount,
                currentPage = currentPage + 1,
                totalPage = sectionPages
            ) { event ->
                when (event) {
                    SectionInventoryEvent.AddSection -> { /* TODO() */
                    }

                    SectionInventoryEvent.NextPage -> {
                        currentPage = (currentPage + 1).coerceAtMost(sectionPages - 1)
                    }

                    SectionInventoryEvent.PreviousPage -> {
                        currentPage = (currentPage - 1).coerceAtLeast(0)
                    }
                }
            }
        }
    }
}

sealed interface SectionInventoryEvent : CircuitUiEvent {
    data object AddSection : SectionInventoryEvent
    data object PreviousPage : SectionInventoryEvent
    data object NextPage : SectionInventoryEvent
}

//@OptIn(ExperimentalCoroutinesApi::class)
//@Inject
//class SectionInventoryViewModel(
//    private val inventoryRepository: InventoryRepository,
//) : ViewModel() {
//
//    private val currentPage: MutableStateFlow<Int> = MutableStateFlow(0)
//
//    val uiState: StateFlow<SectionInventoryUiState> = combine(
//        currentPage,
//        inventoryRepository.getSectionPages(),
//        inventoryRepository.getSectionsCount(),
//        currentPage.flatMapLatest { page -> inventoryRepository.getSections(page) },
//    ) { currentPage, totalPage, sectionsCount, sections ->
//        SectionInventoryUiState.Sections(
//            sectionTable = SectionTable(
//                items = sections.map(Section::toTableItem)
//            ),
//            sectionsCount = sectionsCount,
//            currentPage = currentPage + 1,
//            totalPage = totalPage
//        )
//    }.stateIn(
//        viewModelScope,
//        SharingStarted.WhileSubscribed(5000L),
//        SectionInventoryUiState.Loading,
//    )
//
//    fun addSection() {
//        viewModelScope.launch {
//            inventoryRepository.addSection("Section")
//        }
//    }
//
//    fun fetchPreviousPage() {
//        currentPage.value = (currentPage.value - 1).coerceAtLeast(0)
//    }
//
//    fun fetchNextPage() {
//        val totalPage = (uiState.value as? SectionInventoryUiState.Sections)?.totalPage ?: return
//        currentPage.value = (currentPage.value + 1).coerceAtMost(totalPage - 1)
//    }
//}

sealed interface SectionInventoryUiState : CircuitUiState {

    data object Loading : SectionInventoryUiState

    data class Sections(
        val sectionTable: SectionTable,
        val sectionsCount: Int,
        val currentPage: Int,
        val totalPage: Int,
        val eventSink: (SectionInventoryEvent) -> Unit = {},
    ) : SectionInventoryUiState
}
