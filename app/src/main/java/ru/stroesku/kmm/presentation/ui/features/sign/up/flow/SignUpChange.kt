package ru.stroesku.kmm.presentation.ui.features.sign.up.flow

sealed class SignUpChange {
    object Loading : SignUpChange()

    object SetCodeSubState : SignUpChange()
    object SetPhoneSubState : SignUpChange()
    data class SetValid(
        val phone: String,
        val firstName: String,
        val lastName: String,
        val isValid: Boolean
    ) : SignUpChange()

    object CompleteStep : SignUpChange()
    object HideError : SignUpChange()
    data class ShowError(val message: String) : SignUpChange()
}