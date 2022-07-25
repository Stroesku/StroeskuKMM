package ru.stroesku.kmm.presentation.ui.features.profile.update_phone.flow

import ru.dtk.lib.architecture.mvi.DtkBaseAction as BaseEvent

sealed class UpdatePhoneEvent : BaseEvent {
    object SetPhoneSubState : UpdatePhoneEvent()
    object CloseErrorDialog : UpdatePhoneEvent()
    data class InputPhone(val phone: String) : UpdatePhoneEvent()
    data class SendPhone(val phone: String) : UpdatePhoneEvent()
    data class SendCode(val phone: String, val code: String) : UpdatePhoneEvent()
}