package ru.stroesku.kmm.presentation.ui.features.splash.flow

import ru.dtk.lib.architecture.mvi.DtkBaseAction

sealed class SplashEvent : DtkBaseAction {
    object CheckAuthorization : SplashEvent()
}