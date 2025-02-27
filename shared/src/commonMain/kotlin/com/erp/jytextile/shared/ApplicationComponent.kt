package com.erp.jytextile.shared

import com.erp.jytextile.core.data.di.RepositoryComponent
import com.erp.jytextile.core.database.InventoryDatabaseComponent
import com.erp.jytextile.core.database.di.LocalDataSourceComponent
import com.erp.jytextile.core.network.di.RemoteDataSourceComponent

interface ApplicationComponent :
    InventoryDatabaseComponent,
    RepositoryComponent,
    LocalDataSourceComponent,
    RemoteDataSourceComponent
