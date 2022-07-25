package ru.stroesku.kmm.presentation.ui.features.profile.flow

import ru.dtk.lib.architecture.mvi.DtkBaseState
import ru.stroesku.kmm.domain.model.ProfileModel
import ru.stroesku.kmm.domain.param.ProfileUpdParam

data class ProfileViewState(
    val isEmpty: Boolean = false,
    val param: ProfileUpdParam? = null,
    val model: ProfileModel? = null,
    val isValid: Boolean = false,
    val isLoading: Boolean = false,
    val isShowSuccessDialog: Boolean = false,
    val photoScreen: Boolean = false,
    val errorMessage: String? = null
) : DtkBaseState {

    fun loadingState(): ProfileViewState {
        return copy(
            isEmpty = false,
            isLoading = true
        )
    }

    fun setProfileInfo(model: ProfileModel): ProfileViewState {
        return copy(
            isEmpty = false,
            isLoading = false,
            model = model
        )
    }

    fun photoScreenStateChange(): ProfileViewState {
        return copy(
            isEmpty = false,
            isLoading = false,
            photoScreen = true
        )
    }

    fun paramState(param: ProfileUpdParam, isValid: Boolean): ProfileViewState {
        return copy(
            isEmpty = false,
            param = param,
            isValid = isValid
        )
    }

    fun successUpdateDialogState(newModel: ProfileModel): ProfileViewState {
        return copy(
            isEmpty = false,
            isLoading = false,
            model = newModel
        )
    }

    fun errorState(message: String?): ProfileViewState {
        return copy(
            isEmpty = false,
            isLoading = false,
            errorMessage = message
        )
    }
}