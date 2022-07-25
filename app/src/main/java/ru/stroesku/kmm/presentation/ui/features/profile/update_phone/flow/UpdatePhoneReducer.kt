package ru.stroesku.kmm.presentation.ui.features.profile.update_phone.flow

import ru.dtk.lib.architecture.mvi.DtkReducer

class UpdatePhoneReducer :
    DtkReducer<UpdatePhoneViewState, UpdatePhoneAction> {

    override fun invoke(
        state: UpdatePhoneViewState,
        change: UpdatePhoneAction
    ): UpdatePhoneViewState {
        return when (change) {
            is UpdatePhoneAction.Loading -> state.loadingState()
            is UpdatePhoneAction.SetCodeSubState -> state.setCodeSubState()
            is UpdatePhoneAction.SetPhoneSubState -> state.setPhoneSubState()
            is UpdatePhoneAction.SetValid -> state.setValidState(change.phone, change.isValid)
            is UpdatePhoneAction.CompleteStep -> state.completeStepState()
            is UpdatePhoneAction.ShowError -> state.errorState(change.message)
            is UpdatePhoneAction.HideError -> state.errorState(null)
        }
    }
}