package ru.stroesku.kmm.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("picture")
    val urlsSet: PhotoUrlsSetDto?,
)

@Serializable
data class PhotoUrlsSetDto(
    @SerialName("original")
    val originalUrl: String?,
    @SerialName("100")
    val size100Url: String?,
    @SerialName("50")
    val size50Url: String?,
    @SerialName("24")
    val size24Url: String?
)

fun getEmptyUrlsSet() = PhotoUrlsSetDto("", "", "", "")
