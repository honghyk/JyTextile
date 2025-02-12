package com.erp.jytextile.shared.inject

import com.erp.jytextile.feature.inventory.InventoryComponent
import com.erp.jytextile.feature.root.AppContent
import com.erp.jytextile.feature.root.RootComponent

interface UiComponent
    : InventoryComponent,
    RootComponent {

    val appContent: AppContent
}
