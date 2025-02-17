package com.erp.jytextile.core.base.extension

private const val YARD_PER_METER = 1.09361

fun meterToYard(meter: Double): Double {
    return (meter * YARD_PER_METER)
}

const val DOUBLE_REGEX_PATTERN = "^\\d*\\.?\\d*\$"
