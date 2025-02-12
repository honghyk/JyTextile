package com.erp.jytextile.feature.inventory

import androidx.lifecycle.ViewModel
import com.erp.jytextile.core.domain.repository.InventoryRepository
import me.tatarka.inject.annotations.Inject

@Inject
class InventoryViewModel(
    private val inventoryRepository: InventoryRepository,
) : ViewModel() {

}
