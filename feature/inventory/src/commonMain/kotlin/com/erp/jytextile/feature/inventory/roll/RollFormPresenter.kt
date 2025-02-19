package com.erp.jytextile.feature.inventory.roll

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.erp.jytextile.core.base.circuit.wrapEventSink
import com.erp.jytextile.core.base.extension.DOUBLE_REGEX_PATTERN
import com.erp.jytextile.core.domain.model.FabricRollInsertion
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
class RollFormPresenterFactory(
    private val presenterFactory: (Navigator) -> RollFormPresenter,
) : Presenter.Factory {

    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext
    ): Presenter<*>? {
        return when (screen) {
            is RollFormScreen -> presenterFactory(navigator)
            else -> return null
        }
    }
}

@Inject
class RollFormPresenter(
    @Assisted private val navigator: Navigator,
    private val inventoryRepository: InventoryRepository,
) : Presenter<RollFormUiState> {

    @Composable
    override fun present(): RollFormUiState {
        var zoneName by rememberRetained { mutableStateOf("") }
        var id by rememberRetained { mutableStateOf("") }
        var itemNo by rememberRetained { mutableStateOf("") }
        var orderNo by rememberRetained { mutableStateOf("") }
        var color by rememberRetained { mutableStateOf("") }
        var factory by rememberRetained { mutableStateOf("") }
        var quantity by rememberRetained { mutableStateOf("") }
        var remark by rememberRetained { mutableStateOf("") }
        var lengthUnit by rememberRetained { mutableStateOf(LengthUnit.METER) }

        val eventSink: CoroutineScope.(RollFormEvent) -> Unit = { event ->
            when (event) {
                RollFormEvent.Discard -> navigator.pop()
                RollFormEvent.Submit -> {
                    launch {
                        try {
                            inventoryRepository.addFabricRoll(
                                zoneName = zoneName,
                                rollInsertion = FabricRollInsertion(
                                    id = id.toLong(),
                                    itemNo = itemNo,
                                    orderNo = orderNo,
                                    color = color,
                                    factory = factory,
                                    quantity = quantity.toDouble(),
                                    remark = remark,
                                    lengthUnit = lengthUnit,
                                ),
                            )
                            navigator.pop()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

                is RollFormEvent.UpdateZoneName -> zoneName = event.zoneName
                is RollFormEvent.UpdateId -> {
                    id = event.id.filter { it.isDigit() }
                }

                is RollFormEvent.UpdateItemNo -> itemNo = event.itemNo
                is RollFormEvent.UpdateOrderNo -> orderNo = event.orderNo
                is RollFormEvent.UpdateColor -> color = event.color
                is RollFormEvent.UpdateFactory -> factory = event.factory
                is RollFormEvent.UpdateQuantity -> {
                    if (event.quantity.matches(Regex(DOUBLE_REGEX_PATTERN))) {
                        quantity = event.quantity
                    }
                }

                is RollFormEvent.UpdateRemark -> remark = event.remark
                is RollFormEvent.UpdateLengthUnit -> lengthUnit = event.lengthUnit
            }
        }

        return RollFormUiState(
            zoneName = zoneName,
            id = id,
            itemNo = itemNo,
            orderNo = orderNo,
            color = color,
            factory = factory,
            quantity = quantity,
            lengthUnit = lengthUnit,
            remark = remark,
            eventSink = wrapEventSink(eventSink),
        )
    }
}

data class RollFormUiState(
    val zoneName: String,
    val id: String,
    val itemNo: String,
    val orderNo: String,
    val color: String,
    val factory: String,
    val quantity: String,
    val lengthUnit: LengthUnit,
    val remark: String,
    val eventSink: (RollFormEvent) -> Unit
) : CircuitUiState {
    val canSubmit: Boolean
        get() = zoneName.isNotEmpty() &&
                id.isNotEmpty() &&
                itemNo.isNotEmpty() &&
                orderNo.isNotEmpty() &&
                color.isNotEmpty() &&
                factory.isNotEmpty() &&
                quantity.isNotEmpty()
}

sealed interface RollFormEvent : CircuitUiEvent {
    data class UpdateZoneName(val zoneName: String) : RollFormEvent
    data class UpdateId(val id: String) : RollFormEvent
    data class UpdateItemNo(val itemNo: String) : RollFormEvent
    data class UpdateOrderNo(val orderNo: String) : RollFormEvent
    data class UpdateColor(val color: String) : RollFormEvent
    data class UpdateFactory(val factory: String) : RollFormEvent
    data class UpdateQuantity(val quantity: String) : RollFormEvent
    data class UpdateRemark(val remark: String) : RollFormEvent
    data class UpdateLengthUnit(val lengthUnit: LengthUnit) : RollFormEvent
    data object Submit : RollFormEvent
    data object Discard : RollFormEvent
}
