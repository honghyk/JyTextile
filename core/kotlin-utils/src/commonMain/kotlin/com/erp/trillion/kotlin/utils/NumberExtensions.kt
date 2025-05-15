package com.erp.trillion.kotlin.utils

import kotlin.math.pow
import kotlin.math.round

private const val YARD_PER_METER = 1.09361
private const val METER_PER_YARD = 0.9144

const val DOUBLE_REGEX_PATTERN = "^\\d*\\.?\\d*\$"

fun Double.meterToYard(): Double {
    return this * YARD_PER_METER
}

fun Double.yardToMeter(): Double {
    return this * METER_PER_YARD
}

fun Double.formatDecimal(decimalPlaces: Int): String {
    val factor = 10.0.pow(decimalPlaces)
    return (round(this * factor) / factor).toString()
}
