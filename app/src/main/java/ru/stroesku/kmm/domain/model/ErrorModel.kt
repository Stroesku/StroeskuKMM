package ru.stroesku.kmm.domain.model

import ru.stroesku.kmm.presentation.ui.extension.isNotNullOrEmptyOrBlank

data class ErrorModel(
    val code: Int,
    val message: String,
    val causeMessages: List<String>
) {
    companion object {
        const val EMPTY_CODE = 0
    }

    val userMessage: String =
        if (causeMessages.isNotEmpty())
            causeMessages.first { it.isNotNullOrEmptyOrBlank() }
        else message
}