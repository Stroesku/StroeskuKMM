package ru.stroesku.kmm.presentation.ui.features.schedule.flow

import ru.dtk.lib.architecture.mvi.DtkReducer
import timber.log.Timber

class ScheduleReducer :
    DtkReducer<ScheduleViewState, ScheduleChange> {

    override fun invoke(
        state: ScheduleViewState,
        change: ScheduleChange
    ): ScheduleViewState {
        Timber.e("invoke $change")
        return when (change) {
            is ScheduleChange.Loading -> state.loadingState()
            is ScheduleChange.SetList -> state.setListState(change.list)
            is ScheduleChange.UpdateList -> state.setListState(change.list)
            is ScheduleChange.Error -> state.errorState(change.message)
        }
    }
}