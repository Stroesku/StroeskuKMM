package ru.stroesku.kmm.presentation.ui.features.sign.`in`.flow

import ru.dtk.lib.architecture.mvi.DtkBaseAction

sealed class SignInAction : DtkBaseAction {
    object SetPhoneSubState : SignInAction()
    object CloseErrorDialog : SignInAction()
    data class InputPhone(val phone: String) : SignInAction()
    data class SendPhone(val phone: String) : SignInAction()
    data class SendCode(val phone: String, val code: String) : SignInAction()
}