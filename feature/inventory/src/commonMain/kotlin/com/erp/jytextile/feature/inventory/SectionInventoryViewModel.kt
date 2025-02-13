package com.erp.jytextile.feature.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erp.jytextile.core.domain.model.Section
import com.erp.jytextile.core.domain.repository.InventoryRepository
import com.erp.jytextile.feature.inventory.model.SectionTable
import com.erp.jytextile.feature.inventory.model.toTableItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@Inject
class SectionInventoryViewModel(
    private val inventoryRepository: InventoryRepository,
) : ViewModel() {

    private val currentPage: MutableStateFlow<Int> = MutableStateFlow(0)

    val uiState: StateFlow<SectionInventoryUiState> = combine(
        currentPage,
        inventoryRepository.getSectionPages(),
        inventoryRepository.getSectionsCount(),
        currentPage.flatMapLatest { page -> inventoryRepository.getSections(page) },
    ) { currentPage, totalPage, sectionsCount, sections ->
        SectionInventoryUiState.Sections(
            sectionTable = SectionTable(
                items = sections.map(Section::toTableItem)
            ),
            sectionsCount = sectionsCount,
            currentPage = currentPage + 1,
            totalPage = totalPage
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        SectionInventoryUiState.Loading,
    )

    fun addSection() {
        viewModelScope.launch {
            inventoryRepository.addSection("Section")
        }
    }

    fun fetchPreviousPage() {
        currentPage.value = (currentPage.value - 1).coerceAtLeast(0)
    }

    fun fetchNextPage() {
        val totalPage = (uiState.value as? SectionInventoryUiState.Sections)?.totalPage ?: return
        currentPage.value = (currentPage.value + 1).coerceAtMost(totalPage - 1)
    }
}

sealed interface SectionInventoryUiState {

    data object Loading : SectionInventoryUiState

    data class Sections(
        val sectionTable: SectionTable,
        val sectionsCount: Int = 0,
        val currentPage: Int = 1,
        val totalPage: Int = 1,
    ) : SectionInventoryUiState
}
