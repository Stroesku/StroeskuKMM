package ru.stroesku.kmm.presentation.ui.features.profile.update_phone.flow

sealed class UpdatePhoneAction {
    object Loading : UpdatePhoneAction()

    object SetCodeSubState : UpdatePhoneAction()
    object SetPhoneSubState : UpdatePhoneAction()
    data class SetValid(val phone: String, val isValid: Boolean) : UpdatePhoneAction()

    object CompleteStep : UpdatePhoneAction()

    object HideError : UpdatePhoneAction()
    data class ShowError(val message: String) : UpdatePhoneAction()
}