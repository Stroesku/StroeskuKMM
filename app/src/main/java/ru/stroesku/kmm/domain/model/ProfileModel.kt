package ru.stroesku.kmm.domain.model

data class ProfileModel(
    val id: Int,
    val name: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val birthDate: String,
    val photo: String
)