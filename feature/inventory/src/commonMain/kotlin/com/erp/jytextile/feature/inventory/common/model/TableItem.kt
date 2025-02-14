package com.erp.jytextile.feature.inventory.common.model

interface Table {
    val headers: List<String>
    val items: List<TableItem>
}

interface TableItem {
    val tableRow: List<String>
}
