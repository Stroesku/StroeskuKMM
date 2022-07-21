package ru.stroesku.kmm.presentation.ui.features.splash.flow

import ru.dtk.lib.architecture.mvi.DtkBaseState

data class SplashViewState(
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val toSelectAuth: Boolean = false,
    val toMainScreen: Boolean = false,
    val errorMessage: String? = null
) : DtkBaseState {

    fun loadingState(): SplashViewState {
        return copy(
            isIdle = false,
            isLoading = true
        )
    }

    fun toSelectAuthState(): SplashViewState {
        return copy(
            isIdle = false,
            isLoading = false,
            toSelectAuth = true
        )
    }

    fun toMainScreen(): SplashViewState {
        return copy(
            isIdle = false,
            isLoading = false,
            toMainScreen = true
        )
    }

    fun errorState(message: String): SplashViewState {
        return copy(
            isIdle = false,
            isLoading = false,
            errorMessage = message
        )
    }
}