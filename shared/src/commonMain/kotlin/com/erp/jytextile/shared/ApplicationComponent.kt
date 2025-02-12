package com.erp.jytextile.shared

import com.erp.jytextile.core.data.repository.RepositoryComponent
import com.erp.jytextile.core.database.InventoryDatabaseComponent

interface ApplicationComponent :
    InventoryDatabaseComponent,
    RepositoryComponent
