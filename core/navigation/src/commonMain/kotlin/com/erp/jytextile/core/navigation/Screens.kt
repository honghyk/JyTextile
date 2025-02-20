package com.erp.jytextile.core.navigation

import com.erp.jytextile.core.base.parcel.Parcelize
import com.slack.circuit.runtime.screen.Screen

@Parcelize
data object ZoneFormScreen : Screen

@Parcelize
data object ZoneInventoryScreen : Screen

@Parcelize
data object RollFormScreen : Screen

@Parcelize
data class RollInventoryScreen(
    val zoneId: Long,
) : Screen

@Parcelize
data class ReleaseFormScreen(
    val rollId: Long,
    val rollItemNo: String
) : Screen

@Parcelize
data class ReleaseHistoryScreen(
    val rollId: Long,
) : Screen

@Parcelize
data class RollDetailScreen(
    val rollId: Long
) : Screen

@Parcelize
data object SearchScreen : Screen
