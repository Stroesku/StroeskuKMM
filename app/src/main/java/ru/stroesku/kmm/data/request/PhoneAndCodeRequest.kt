package ru.stroesku.kmm.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhoneAndCodeRequest(
    @SerialName("phone")
    val phone: String,
    @SerialName("code")
    val code: String
)