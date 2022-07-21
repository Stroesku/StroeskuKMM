package ru.stroesku.kmm.data.response


data class ErrorResponse(
    val message: String,
    val errors: Map<String, String>
) {
}