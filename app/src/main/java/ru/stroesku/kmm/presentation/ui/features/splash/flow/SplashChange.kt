package ru.stroesku.kmm.presentation.ui.features.splash.flow

sealed class SplashChange {
    object Loading : SplashChange()
    object ToMainScreen : SplashChange()
    object ToSelectAuth : SplashChange()
    data class Error(val message: String) : SplashChange()
}