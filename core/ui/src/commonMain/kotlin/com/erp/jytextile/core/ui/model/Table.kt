package com.erp.jytextile.core.ui.model

/**
 * @property headers The list of column headers displayed in the table.
 * @property items The list of table rows (data items) for the current page.
 * @property currentPage The index of the currently displayed page, starting from `0`.
 * @property totalPage The total number of pages available in the table.
 */
interface Table {
    val headers: List<String>
    val items: List<TableItem>
    val currentPage: Int
    val totalPage: Int
}

interface TableItem {
    val id: Long
    val tableRow: List<String>
}
