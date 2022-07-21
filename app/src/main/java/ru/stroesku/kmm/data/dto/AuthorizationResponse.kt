package ru.stroesku.kmm.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationResponse(
    @SerialName("token_type")
    val tokenType: String,
    @SerialName("token")
    val token: String
)