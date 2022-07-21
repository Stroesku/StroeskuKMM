package ru.stroesku.kmm.presentation.ui.features.sign.`in`.flow

sealed class SignInChange {
    object Loading : SignInChange()

    object SetCodeSubState : SignInChange()
    object SetPhoneSubState : SignInChange()
    data class SetValid(val phone: String, val isValid: Boolean) : SignInChange()

    object CompleteStep : SignInChange()

    object HideError : SignInChange()
    data class ShowError(val message: String) : SignInChange()
}