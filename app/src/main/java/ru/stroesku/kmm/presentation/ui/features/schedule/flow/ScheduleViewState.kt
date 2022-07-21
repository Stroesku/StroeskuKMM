package ru.stroesku.kmm.presentation.ui.features.schedule.flow

import ru.stroesku.kmm.presentation.ui.features.schedule.ScheduleDay
import ru.dtk.lib.architecture.mvi.DtkBaseState

data class ScheduleViewState(
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val list: List<ScheduleDay>? = null,
    val errorMessage: String? = null
) : DtkBaseState {

    fun loadingState(): ScheduleViewState {
        return copy(
            isIdle = false,
            isLoading = true
        )
    }

    fun setListState(list: List<ScheduleDay>): ScheduleViewState {
        return copy(
            isIdle = false,
            isLoading = false,
            list = list
        )
    }

    fun errorState(message: String): ScheduleViewState {
        return copy(
            isIdle = false,
            isLoading = false,
            errorMessage = message
        )
    }
}