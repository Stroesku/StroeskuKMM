package ru.stroesku.kmm.presentation.ui.features.profile.flow

import ru.stroesku.kmm.domain.param.ProfileUpdParam
import ru.dtk.lib.architecture.mvi.DtkBaseAction as BaseEvent

sealed class ProfileEvent : BaseEvent {
    data class Validate(val param: ProfileUpdParam) : ProfileEvent()
    data class UpdateProfile(val param: ProfileUpdParam) : ProfileEvent()
    object OpenPhotoScreen : ProfileEvent()
    object GetProfile : ProfileEvent()
}