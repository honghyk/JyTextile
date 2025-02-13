package com.erp.jytextile.feature.inventory.model

interface Table {
    val headers: List<String>
    val items: List<TableItem>
}

interface TableItem {
    val tableRow: List<String>
}
