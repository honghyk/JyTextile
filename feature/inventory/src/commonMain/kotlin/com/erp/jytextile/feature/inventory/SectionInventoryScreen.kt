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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.erp.jytextile.core.designsystem.component.JyButton
import com.erp.jytextile.feature.inventory.component.InventoryOverallPanel
import com.erp.jytextile.feature.inventory.component.InventoryTablePanel
import com.erp.jytextile.feature.inventory.model.SectionTable

@Composable
fun SectionInventoryScreen(
    viewModel: SectionInventoryViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SectionInventoryScreen(
        uiState = uiState,
        onAddSectionClick = viewModel::addSection,
        onPreviousClick = viewModel::fetchPreviousPage,
        onNextClick = viewModel::fetchNextPage,
        modifier = modifier,
    )
}

@Composable
private fun SectionInventoryScreen(
    uiState: SectionInventoryUiState,
    onAddSectionClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(
                horizontal = 32.dp,
                vertical = 22.dp,
            ),
    ) {
        when (uiState) {
            SectionInventoryUiState.Loading -> { /* TODO */
            }

            is SectionInventoryUiState.Sections -> {
                SectionInventory(
                    table = uiState.sectionTable,
                    sectionsCount = uiState.sectionsCount,
                    currentPage = uiState.currentPage,
                    totalPage = uiState.totalPage,
                    onAddSectionClick = onAddSectionClick,
                    onPreviousClick = onPreviousClick,
                    onNextClick = onNextClick,
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
