package ru.stroesku.kmm.presentation.ui.features.profile.flow

import ru.dtk.lib.architecture.mvi.DtkReducer

class ProfileReducer : DtkReducer<ProfileViewState, ProfileAction> {

    override fun invoke(
        state: ProfileViewState,
        change: ProfileAction
    ): ProfileViewState {
        return when (change) {
            is ProfileAction.Loading -> state.loadingState()
            is ProfileAction.SetProfileInfo -> state.setProfileInfo(change.model)
            is ProfileAction.OpenPhotoScreen -> state.photoScreenStateChange()
            is ProfileAction.ValidParam -> state.paramState(change.model, change.isValid)
            is ProfileAction.SuccessUpdate -> state.successUpdateDialogState(change.newModel)
            is ProfileAction.Error -> state.errorState(change.message)
            else -> state.copy(isEmpty = true)
        }
    }
}