package ru.stroesku.kmm.presentation.ui.extension

import java.text.NumberFormat

fun String?.exactlyNumbers(): Boolean {
    val pattern = "[a-zA-Z']+".toRegex()
    return this?.contains(pattern) == false
}

fun Double.toPriceFormat(): String {
    val format: NumberFormat =
        NumberFormat.getCurrencyInstance().apply { maximumFractionDigits = 0 }
    return format.format(this)
}

fun Double.toDiscountFormat(): String {
    val format: NumberFormat =
        NumberFormat.getCurrencyInstance().apply { maximumFractionDigits = 0 }
    return "-${format.format(this)}"
}

fun Int.fromCoinToRoubles() = this.div(100).toDouble()

fun Float.fromRoublesToCoin() = this.times(100).toDouble()

fun bindStrings(argument: Any, format: String): String {
    return String.format(format, argument)
}

inline fun <reified T> isEqual(first: List<T>, second: List<T>): Boolean {
    if (first.size != second.size) {
        return false
    }
    return first.toTypedArray() contentEquals second.toTypedArray()
}

fun String?.isNotNullOrEmptyOrBlank(): Boolean {
    return !this.isNullOrEmpty() && !this.isNullOrBlank()
}

fun String.isNotEmptyOrBlank(): Boolean {
    return isNotEmpty() && isNotBlank()
}

fun Int.isNotZero(): Boolean {
    return this != 0
}

fun Float.isNotZero(): Boolean {
    return this != 0f
}

fun Double.isNotZero(): Boolean {
    return this != 0.0
}

fun String.toPhoneFormat(): String {
    return if (this.contains("+")) this else
        "+$this"
}

fun Any?.isNotNull(): Boolean {
    return this != null
}

fun Double?.orZero(): Double {
    return this ?: 0.0
}

fun Int?.orZero(): Int {
    return this ?: 0
}

fun Long?.orZero(): Long {
    return this ?: 0
}