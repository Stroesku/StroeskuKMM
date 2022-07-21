package ru.stroesku.kmm.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CleanerDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("created_at")
    val createdAt: Long?,
    @SerialName("name")
    val name: String?,
    @SerialName("first_name")
    val firstName: String?,
    @SerialName("last_name")
    val lastName: String?,
    @SerialName("phone")
    val phone: String?,
    @SerialName("birth_date")
    val dateOfBirth: String?,
    @SerialName("picture")
    val picture: PhotoUrlsSetDto?,
)