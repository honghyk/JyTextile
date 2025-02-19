package com.erp.jytextile.feature.form.zone

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.erp.jytextile.core.base.circuit.wrapEventSink
import com.erp.jytextile.core.domain.repository.InventoryRepository
import com.erp.jytextile.core.navigation.ZoneFormScreen
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
class ZoneFormPresenterFactory(
    private val presenterFactory: (Navigator) -> ZoneFormPresenter,
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext
    ): Presenter<*>? {
        return when (screen) {
            is ZoneFormScreen -> presenterFactory(navigator)
            else -> return null
        }
    }
}

@Inject
class ZoneFormPresenter(
    @Assisted private val navigator: Navigator,
    private val inventoryRepository: InventoryRepository,
) : Presenter<ZoneFormUiState> {

    @Composable
    override fun present(): ZoneFormUiState {
        var name by rememberRetained { mutableStateOf("") }

        val eventSink: CoroutineScope.(ZoneFormEvent) -> Unit = { event ->
            when (event) {
                is ZoneFormEvent.UpdateName -> {
                    name = event.name
                }

                ZoneFormEvent.Submit -> {
                    launch {
                        try {
                            inventoryRepository.addZone(name)
                            navigator.pop()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                ZoneFormEvent.Discard -> {
                    navigator.pop()
                }
            }
        }

        return ZoneFormUiState(
            name = name,
            eventSink = wrapEventSink(eventSink)
        )
    }
}

data class ZoneFormUiState(
    val name: String,
    val eventSink: (ZoneFormEvent) -> Unit
) : CircuitUiState {
    val submittable: Boolean
        get() = name.isNotEmpty()
}

sealed interface ZoneFormEvent : CircuitUiEvent {
    data class UpdateName(val name: String) : ZoneFormEvent
    data object Submit : ZoneFormEvent
    data object Discard : ZoneFormEvent
}
