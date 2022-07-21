package ru.stroesku.kmm.presentation.ui.features.sign.up.flow

import ru.dtk.lib.architecture.mvi.DtkBaseState

enum class SignUpSubState { Phone, Code }

data class SignUpViewState(
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val isValid: Boolean = false,
    val subState: SignUpSubState = SignUpSubState.Phone,
    val isComplete: Boolean = false,
    val phone: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val errorMessage: String? = null
) : DtkBaseState {

    fun loadingState(): SignUpViewState {
        return copy(
            isIdle = false,
            isLoading = true
        )
    }

    fun setValidState(
        phone: String,
        firstName: String,
        lastName: String,
        isValid: Boolean
    ): SignUpViewState {
        return copy(
            isIdle = false,
            isLoading = false,
            phone = phone,
            firstName = firstName,
            lastName = lastName,
            isValid = isValid,
        )
    }

    fun setCodeSubState(): SignUpViewState {
        return copy(
            isIdle = false,
            isLoading = false,
            subState = SignUpSubState.Code
        )
    }

    fun setPhoneSubState(): SignUpViewState {
        return copy(
            isIdle = false,
            isLoading = false,
            subState = SignUpSubState.Phone
        )
    }

    fun completeStepState(): SignUpViewState {
        return copy(
            isIdle = false,
            isLoading = false,
            isComplete = true
        )
    }

    fun errorState(message: String?): SignUpViewState {
        return copy(
            isIdle = false,
            isLoading = false,
            errorMessage = message
        )
    }
}