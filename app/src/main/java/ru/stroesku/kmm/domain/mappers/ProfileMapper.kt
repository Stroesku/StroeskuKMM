package ru.stroesku.kmm.domain.mappers


import ru.stroesku.kmm.data.dto.ProfileDto
import ru.stroesku.kmm.domain.model.ProfileModel
import ru.stroesku.kmm.presentation.ui.extension.orZero
import ru.stroesku.kmm.presentation.ui.utils.dateDMYFormat
import ru.stroesku.kmm.presentation.ui.utils.dateYMDFormatParse

class ProfileMapper : Mapper<ProfileDto, ProfileModel> {

    override fun map(input: ProfileDto): ProfileModel {
        with(input) {
            val dateOfBirth = dateOfBirth?.dateYMDFormatParse()?.dateDMYFormat().orEmpty()

            return ProfileModel(
                id = id.orZero(),
                birthDate = dateOfBirth,
                name = name.orEmpty(),
                firstName = firstName.orEmpty(),
                lastName = lastName.orEmpty(),
                email = email.orEmpty(),
                phone = phone.orEmpty(),
                photo = picture?.originalUrl.orEmpty()
            )
        }
    }
}