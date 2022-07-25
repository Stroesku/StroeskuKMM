package ru.stroesku.kmm.presentation.ui.features.sign.up.flow

import ru.stroesku.kmm.domain.param.SignParam
import ru.dtk.lib.architecture.mvi.DtkBaseAction

sealed class SignUpAction : DtkBaseAction {
    object SetPhoneSubState : SignUpAction()
    object CloseErrorDialog : SignUpAction()

    data class Validate(val param: SignParam) : SignUpAction()

    data class SendPhone(val phone: String) : SignUpAction()
    data class SendCode(val param: SignParam) : SignUpAction()
}