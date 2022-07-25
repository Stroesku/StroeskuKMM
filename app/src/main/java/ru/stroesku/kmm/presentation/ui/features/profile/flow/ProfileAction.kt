package ru.stroesku.kmm.presentation.ui.features.profile.flow

import ru.stroesku.kmm.domain.model.ProfileModel
import ru.stroesku.kmm.domain.param.ProfileUpdParam

sealed class ProfileAction {
    //permanent
    object Empty : ProfileAction()
    object Loading : ProfileAction()
    data class SetProfileInfo(val model: ProfileModel) : ProfileAction()
    data class ValidParam(val model: ProfileUpdParam, val isValid: Boolean) : ProfileAction()
    //one shot
    object OpenPhotoScreen : ProfileAction()
    data class SuccessUpdate(val newModel: ProfileModel) : ProfileAction()
    data class Error(val message: String) : ProfileAction()
}