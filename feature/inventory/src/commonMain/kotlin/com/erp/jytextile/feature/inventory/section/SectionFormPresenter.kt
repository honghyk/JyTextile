package com.erp.jytextile.feature.inventory.section

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.erp.jytextile.core.base.circuit.wrapEventSink
import com.erp.jytextile.core.domain.repository.InventoryRepository
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class SectionFormPresenterFactory(
    private val presenterFactory: (Navigator) -> SectionFormPresenter,
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext
    ): Presenter<*>? {
        return when (screen) {
            is AddSectionScreen -> presenterFactory(navigator)
            else -> return null
        }
    }
}

@Inject
class SectionFormPresenter(
    @Assisted private val navigator: Navigator,
    private val inventoryRepository: InventoryRepository,
) : Presenter<SectionFormUiState> {

    @Composable
    override fun present(): SectionFormUiState {
        var name by rememberRetained { mutableStateOf("") }

        val eventSink: CoroutineScope.(SectionFormEvent) -> Unit = { event ->
            when (event) {
                is SectionFormEvent.UpdateName -> {
                    name = event.name
                }

                SectionFormEvent.Submit -> {
                    launch {
                        try {
                            inventoryRepository.addSection(name)
                            navigator.pop()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                SectionFormEvent.Discard -> {
                    navigator.pop()
                }
            }
        }

        return SectionFormUiState(
            name = name,
            eventSink = wrapEventSink(eventSink)
        )
    }
}

data class SectionFormUiState(
    val name: String,
    val eventSink: (SectionFormEvent) -> Unit
) : CircuitUiState {
    val submittable: Boolean
        get() = name.isNotEmpty()
}

sealed interface SectionFormEvent : CircuitUiEvent {
    data class UpdateName(val name: String) : SectionFormEvent
    data object Submit : SectionFormEvent
    data object Discard : SectionFormEvent
}
