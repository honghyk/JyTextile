package com.erp.jytextile.core.base.extension

private const val YARD_PER_METER = 1.09361

fun meterToYard(meter: Int): Int {
    return (meter * YARD_PER_METER).toInt()
}
