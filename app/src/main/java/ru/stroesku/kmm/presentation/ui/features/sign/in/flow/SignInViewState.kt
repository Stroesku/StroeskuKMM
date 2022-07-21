package ru.stroesku.kmm.presentation.ui.features.sign.`in`.flow

import ru.dtk.lib.architecture.mvi.DtkBaseState

enum class SignInSubState { Phone, Code }

data class SignInViewState(
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val isValid: Boolean = false,
    val subState: SignInSubState = SignInSubState.Phone,
    val isComplete: Boolean = false,
    val phone: String = "",
    val errorMessage: String? = null
) : DtkBaseState {

    fun loadingState(): SignInViewState {
        return copy(
            isIdle = false,
            isLoading = true
        )
    }

    fun setValidState(phone: String, isValid: Boolean): SignInViewState {
        return copy(
            isIdle = false,
            isLoading = false,
            isValid = isValid,
            phone = phone
        )
    }

    fun setCodeSubState(): SignInViewState {
        return copy(
            isIdle = false,
            isLoading = false,
            subState = SignInSubState.Code
        )
    }

    fun setPhoneSubState(): SignInViewState {
        return copy(
            isIdle = false,
            isLoading = false,
            subState = SignInSubState.Phone
        )
    }

    fun completeStepState(): SignInViewState {
        return copy(
            isIdle = false,
            isLoading = false,
            isComplete = true
        )
    }

    fun errorState(message: String?): SignInViewState {
        return copy(
            isIdle = false,
            isLoading = false,
            errorMessage = message
        )
    }
}