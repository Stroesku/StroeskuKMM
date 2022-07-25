package ru.stroesku.kmm.domain.param

import java.io.File

data class ProfileUpdParam(
    var firstName: String? = null,
    var lastname: String? = null,
    var dateOfBirth: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var photo: File? = null
)