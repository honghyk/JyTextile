package com.erp.jytextile.kotlin.utils

private const val YARD_PER_METER = 1.09361
private const val METER_PER_YARD = 0.9144

const val DOUBLE_REGEX_PATTERN = "^\\d*\\.?\\d*\$"

fun Double.meterToYard(): Double {
    return this * YARD_PER_METER
}

fun Double.yardToMeter(): Double {
    return this * METER_PER_YARD
}
