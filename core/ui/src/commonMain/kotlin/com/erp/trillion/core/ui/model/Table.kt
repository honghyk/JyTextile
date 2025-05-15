package com.erp.trillion.core.ui.model

/**
 * @property headers The list of column headers displayed in the table.
 * @property items The list of table rows (data items) for the current page.
 */
interface Table {
    val headers: List<String>
    val items: List<TableItem>
}

interface TableItem {
    val id: Long
    val tableRow: List<String>
}
