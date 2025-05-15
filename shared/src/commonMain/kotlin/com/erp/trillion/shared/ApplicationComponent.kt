package com.erp.trillion.shared

import com.erp.trillion.core.data.di.RepositoryComponent
import com.erp.trillion.core.database.InventoryDatabaseComponent
import com.erp.trillion.core.database.di.LocalDataSourceComponent
import com.erp.trillion.core.network.di.RemoteDataSourceComponent

interface ApplicationComponent :
    InventoryDatabaseComponent,
    RepositoryComponent,
    LocalDataSourceComponent,
    RemoteDataSourceComponent
