package com.erp.jytextile.core.data.repository

import com.erp.jytextile.core.domain.repository.InventoryRepository
import me.tatarka.inject.annotations.Provides

interface RepositoryComponent {

    @Provides
    fun bindInventoryRepository(impl: InventoryRepositoryImpl): InventoryRepository = impl
}
