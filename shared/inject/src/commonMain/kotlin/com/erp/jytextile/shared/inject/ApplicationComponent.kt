package com.erp.jytextile.shared.inject

import com.erp.jytextile.shared.data.repository.RepositoryComponent
import com.erp.jytextile.shared.database.InventoryDatabaseComponent

interface ApplicationComponent :
    InventoryDatabaseComponent,
    RepositoryComponent
