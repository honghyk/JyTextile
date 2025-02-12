package com.erp.jytextile.shared.data.repository

import com.erp.jytextile.shared.domain.repository.InventoryRepository
import me.tatarka.inject.annotations.Provides

interface RepositoryComponent {

    @Provides
    fun bindInventoryRepository(impl: InventoryRepositoryImpl): InventoryRepository = impl
}
