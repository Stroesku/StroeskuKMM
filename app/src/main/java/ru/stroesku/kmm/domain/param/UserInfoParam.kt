package ru.stroesku.kmm.domain.param


data class UserInfoParam(
    var firstName: String? = null,
    var lastName: String? = null,
    var phone: String? = null,
    var code: String? = null
)
