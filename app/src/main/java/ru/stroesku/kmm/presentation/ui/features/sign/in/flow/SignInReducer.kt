package ru.stroesku.kmm.presentation.ui.features.sign.`in`.flow

import ru.dtk.lib.architecture.mvi.DtkReducer
import ru.stroesku.kmm.presentation.ui.features.sign.`in`.flow.SignInChange

class SignInReducer :
    DtkReducer<SignInViewState, SignInChange> {

    override fun invoke(
        state: SignInViewState,
        change: SignInChange
    ): SignInViewState {
        return when (change) {
            is SignInChange.Loading -> state.loadingState()
            is SignInChange.SetCodeSubState -> state.setCodeSubState()
            is SignInChange.SetPhoneSubState -> state.setPhoneSubState()
            is SignInChange.SetValid -> state.setValidState(change.phone, change.isValid)
            is SignInChange.CompleteStep -> state.completeStepState()
            is SignInChange.ShowError -> state.errorState(change.message)
            is SignInChange.HideError -> state.errorState(null)
        }
    }
}