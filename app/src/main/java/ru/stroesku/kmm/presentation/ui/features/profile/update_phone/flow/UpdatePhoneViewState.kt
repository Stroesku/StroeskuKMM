package ru.stroesku.kmm.presentation.ui.features.profile.update_phone.flow

import ru.dtk.lib.architecture.mvi.DtkBaseState

enum class UpdatePhoneSubState { Phone, Code }

data class UpdatePhoneViewState(
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val isValid: Boolean = false,
    val subState: UpdatePhoneSubState = UpdatePhoneSubState.Phone,
    val isComplete: Boolean = false,
    val phone: String = "",
    val errorMessage: String? = null
) : DtkBaseState {

    fun loadingState(): UpdatePhoneViewState {
        return copy(
            isIdle = false,
            isLoading = true
        )
    }

    fun setValidState(phone: String, isValid: Boolean): UpdatePhoneViewState {
        return copy(
            isIdle = false,
            isLoading = false,
            isValid = isValid,
            phone = phone
        )
    }

    fun setCodeSubState(): UpdatePhoneViewState {
        return copy(
            isIdle = false,
            isLoading = false,
            subState = UpdatePhoneSubState.Code
        )
    }

    fun setPhoneSubState(): UpdatePhoneViewState {
        return copy(
            isIdle = false,
            isLoading = false,
            subState = UpdatePhoneSubState.Phone
        )
    }

    fun completeStepState(): UpdatePhoneViewState {
        return copy(
            isIdle = false,
            isLoading = false,
            isComplete = true
        )
    }

    fun errorState(message: String?): UpdatePhoneViewState {
        return copy(
            isIdle = false,
            isLoading = false,
            errorMessage = message
        )
    }
}