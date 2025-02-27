package com.erp.jytextile.core.data.store

import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.StoreBuilder

data class PagingKey(
    val page: Int,
    val pageSize: Int,
)

inline fun <Key : Any, Network : Any, Output : Any> storeBuilder(
    fetcher: Fetcher<Key, Network>,
    sourceOfTruth: SourceOfTruth<Key, Network, Output>,
) = StoreBuilder.from(fetcher = fetcher, sourceOfTruth = sourceOfTruth)

inline fun <Key : Any, Network : Any> storeBuilder(
    fetcher: Fetcher<Key, Network>,
) = StoreBuilder.from(fetcher = fetcher)
