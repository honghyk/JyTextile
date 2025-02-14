package com.erp.jytextile.feature.inventory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erp.jytextile.core.designsystem.component.JyButton
import com.erp.jytextile.feature.inventory.component.InventoryOverallPanel
import com.erp.jytextile.feature.inventory.component.InventoryTablePanel
import com.erp.jytextile.feature.inventory.model.SectionTable
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject

@Inject
class SectionInventoryUiFactory : Ui.Factory {
    override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
        is SectionInventoryScreen -> {
            ui<SectionInventoryUiState> { state, modifier ->
                SectionInventoryUi(state, modifier)
            }
        }

        else -> null
    }
}


@Composable
fun SectionInventoryUi(
    state: SectionInventoryUiState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(
                horizontal = 32.dp,
                vertical = 22.dp,
            ),
    ) {
        when (state) {
            SectionInventoryUiState.Loading -> { /* TODO */
            }

            is SectionInventoryUiState.Sections -> {
                SectionInventory(
                    table = state.sectionTable,
                    sectionsCount = state.sectionsCount,
                    currentPage = state.currentPage,
                    totalPage = state.totalPage,
                    onAddSectionClick = { state.eventSink(SectionInventoryEvent.AddSection) },
                    onPreviousClick = { state.eventSink(SectionInventoryEvent.PreviousPage) },
                    onNextClick = { state.eventSink(SectionInventoryEvent.NextPage) },
                )
            }
        }
    }
}

@Composable
fun SectionInventory(
    table: SectionTable,
    sectionsCount: Int,
    currentPage: Int,
    totalPage: Int,
    onAddSectionClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        InventoryOverallPanel(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            total = sectionsCount,
            title = "Sections",
        )
        Spacer(modifier = Modifier.height(22.dp))
        InventoryTablePanel(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            title = "Sections",
            table = table,
            currentPage = currentPage,
            totalPage = totalPage,
            onPreviousClick = onPreviousClick,
            onNextClick = onNextClick,
        ) {
            JyButton(
                onClick = onAddSectionClick,
            ) {
                Text(
                    maxLines = 1,
                    text = "Add Section"
                )
            }
        }
    }
}
