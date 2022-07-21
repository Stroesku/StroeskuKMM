package ru.stroesku.kmm.presentation.ui.features.splash.flow

import ru.dtk.lib.architecture.mvi.DtkReducer

class SplashReducer :
    DtkReducer<SplashViewState, SplashChange> {

    override fun invoke(
        state: SplashViewState,
        change: SplashChange
    ): SplashViewState {
        return when (change) {
            is SplashChange.Loading -> state.loadingState()
            is SplashChange.ToMainScreen -> state.toMainScreen()
            is SplashChange.ToSelectAuth -> state.toSelectAuthState()
            is SplashChange.Error -> state.errorState(change.message)
        }
    }
}