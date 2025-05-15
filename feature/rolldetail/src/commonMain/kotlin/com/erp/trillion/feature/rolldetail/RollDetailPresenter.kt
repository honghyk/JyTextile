package com.erp.trillion.feature.rolldetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.erp.trillion.core.base.circuit.showInDialog
import com.erp.trillion.core.base.circuit.wrapEventSink
import com.erp.trillion.core.domain.model.FabricRoll
import com.erp.trillion.core.domain.repository.RollInventoryRepository
import com.erp.trillion.core.navigation.ReleaseFormScreen
import com.erp.trillion.core.navigation.RollDetailScreen
import com.erp.trillion.core.ui.model.FabricRollUiModel
import com.erp.trillion.core.ui.model.toUiModel
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class RollDetailPresenterFactory(
    private val presenterFactory: (Navigator, Long) -> RollDetailPresenter,
) : Presenter.Factory {
    override fun create(
        screen: Screen,
        navigator: Navigator,
        context: CircuitContext
    ): Presenter<*>? {
        return when (screen) {
            is RollDetailScreen -> presenterFactory(navigator, screen.rollId)
            else -> null
        }
    }
}

@Inject
class RollDetailPresenter(
    @Assisted private val navigator: Navigator,
    @Assisted private val rollId: Long,
    private val rollInventoryRepository: RollInventoryRepository,
) : Presenter<RollDetailUiState> {

    @Composable
    override fun present(): RollDetailUiState {
        val overlayHost = LocalOverlayHost.current

        val roll: FabricRollUiModel? by rememberRetained {
            rollInventoryRepository
                .getRoll(rollId)
                .map(FabricRoll::toUiModel)
        }.collectAsRetainedState(null)

        val eventSink: CoroutineScope.(RollDetailEvent) -> Unit = { event ->
            when (event) {

                is RollDetailEvent.Release -> {
                    roll?.let { roll ->
                        launch {
                            overlayHost.showInDialog(
                                ReleaseFormScreen(roll.id, roll.itemNo),
                                navigator::goTo
                            )
                        }
                    }
                }
            }
        }

        return if (roll == null) {
            RollDetailUiState.Loading
        } else {
            RollDetailUiState.RollDetail(
                roll!!,
                eventSink = wrapEventSink(eventSink),
            )
        }
    }
}

sealed interface RollDetailUiState : CircuitUiState {
    data object Loading : RollDetailUiState

    data class RollDetail(
        val roll: FabricRollUiModel,
        val eventSink: (RollDetailEvent) -> Unit = {},
    ) : RollDetailUiState
}

sealed interface RollDetailEvent : CircuitUiEvent {
    data object Release : RollDetailEvent
}
