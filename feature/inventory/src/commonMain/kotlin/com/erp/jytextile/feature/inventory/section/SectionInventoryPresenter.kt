package com.erp.jytextile.feature.inventory.section

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.erp.jytextile.core.base.parcel.Parcelize
import com.erp.jytextile.core.domain.model.Section
import com.erp.jytextile.core.domain.repository.InventoryRepository
import com.erp.jytextile.feature.inventory.common.model.SectionTable
import com.erp.jytextile.feature.inventory.common.model.toTableItem
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.screen.StaticScreen
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Parcelize
data object SectionInventoryScreen : StaticScreen

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
            SectionInventoryScreen -> presenterFactory(navigator)
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

sealed interface SectionInventoryEvent : CircuitUiEvent {
    data object AddSection : SectionInventoryEvent
    data object PreviousPage : SectionInventoryEvent
    data object NextPage : SectionInventoryEvent
}
