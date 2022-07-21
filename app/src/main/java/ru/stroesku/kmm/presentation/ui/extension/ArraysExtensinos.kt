package ru.stroesku.kmm.presentation.ui.extension

fun CharArray.getOrEmpty(index: Int): String {
    return try {
        this[index].toString()
    } catch (e: Exception) {
        ""
    }
}
