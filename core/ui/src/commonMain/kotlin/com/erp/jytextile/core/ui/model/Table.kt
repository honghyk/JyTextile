package com.erp.jytextile.core.ui.model

interface Table {
    val headers: List<String>
    val items: List<TableItem>
}

interface TableItem {
    val id: Long
    val tableRow: List<String>
}
