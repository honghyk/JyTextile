package com.erp.jytextile.feature.inventory.release

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.erp.jytextile.core.base.circuit.wrapEventSink
import com.erp.jytextile.core.base.extension.DOUBLE_REGEX_PATTERN
import com.erp.jytextile.core.domain.model.LengthUnit
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
class ReleaseFormPresenterFactory(
    private val presenterFactory: (Navigator, Long, String) -> ReleaseFormPresenter,
) : Presenter.Factory {

    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext
    ): Presenter<*>? {
        return when (screen) {
            is ReleaseFormScreen -> presenterFactory(navigator, screen.rollId, screen.rollItemNo)
            else -> return null
        }
    }
}

@Inject
class ReleaseFormPresenter(
    @Assisted private val navigator: Navigator,
    @Assisted private val rollId: Long,
    @Assisted private val rollItemNo: String,
    private val inventoryRepository: InventoryRepository,
) : Presenter<ReleaseFormUiState> {

    @Composable
    override fun present(): ReleaseFormUiState {
        var buyer by rememberRetained { mutableStateOf("") }
        var quantity by rememberRetained { mutableStateOf("") }
        var lengthUnit by rememberRetained { mutableStateOf(LengthUnit.METER) }
        var releaseDate by rememberRetained { mutableStateOf("") }

        val eventSink: CoroutineScope.(ReleaseFormEvent) -> Unit = { event ->
            when (event) {
                ReleaseFormEvent.Discard -> navigator.pop()
                ReleaseFormEvent.Submit -> {
                    launch {
                        try {
                            inventoryRepository.releaseFabricRoll(
                                rollId = rollId,
                                buyer = buyer,
                                quantity = quantity.toDouble(),
                                lengthUnit = lengthUnit,
                                releaseDate = releaseDate,
                            )
                            navigator.pop()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                is ReleaseFormEvent.UpdateBuyer -> buyer = event.buyer
                is ReleaseFormEvent.UpdateQuantity -> {
                    if (event.quantity.matches(Regex(DOUBLE_REGEX_PATTERN))) {
                        quantity = event.quantity
                    }
                }

                is ReleaseFormEvent.UpdateLengthUnit -> lengthUnit = event.lengthUnit

                is ReleaseFormEvent.UpdateReleaseDate -> {
                    releaseDate = event.releaseDate
                        .filter { it.isDigit() }
                        .take(8)
                }
            }
        }
        return ReleaseFormUiState(
            rollId = rollId.toString(),
            rollItemNo = rollItemNo,
            buyer = buyer,
            quantity = quantity,
            lengthUnit = lengthUnit,
            releaseDate = releaseDate,
            eventSink = wrapEventSink(eventSink),
        )
    }

}

data class ReleaseFormUiState(
    val rollId: String,
    val rollItemNo: String,
    val buyer: String,
    val quantity: String,
    val lengthUnit: LengthUnit,
    val releaseDate: String,
    val eventSink: (ReleaseFormEvent) -> Unit,
) : CircuitUiState {
    val canSubmit: Boolean
        get() = buyer.isNotEmpty() &&
                quantity.isNotEmpty() &&
                releaseDate.length == 8
}

sealed interface ReleaseFormEvent : CircuitUiEvent {
    data class UpdateBuyer(val buyer: String) : ReleaseFormEvent
    data class UpdateQuantity(val quantity: String) : ReleaseFormEvent
    data class UpdateLengthUnit(val lengthUnit: LengthUnit) : ReleaseFormEvent
    data class UpdateReleaseDate(val releaseDate: String) : ReleaseFormEvent
    data object Submit : ReleaseFormEvent
    data object Discard : ReleaseFormEvent
}
