package ru.stroesku.kmm.presentation.ui.features.sign.up.flow

import ru.stroesku.kmm.presentation.ui.features.sign.up.flow.SignUpViewState
import ru.dtk.lib.architecture.mvi.DtkReducer

class SignUpReducer :
    DtkReducer<SignUpViewState, SignUpChange> {

    override fun invoke(
        state: SignUpViewState,
        change: SignUpChange
    ): SignUpViewState {
        return when (change) {
            is SignUpChange.Loading -> state.loadingState()
            is SignUpChange.SetCodeSubState -> state.setCodeSubState()
            is SignUpChange.SetPhoneSubState -> state.setPhoneSubState()
            is SignUpChange.SetValid -> state.setValidState(
                phone = change.phone,
                firstName = change.firstName,
                lastName = change.lastName,
                isValid = change.isValid
            )
            is SignUpChange.CompleteStep -> state.completeStepState()
            is SignUpChange.ShowError -> state.errorState(change.message)
            is SignUpChange.HideError -> state.errorState(null)
        }
    }
}