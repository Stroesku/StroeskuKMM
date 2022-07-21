package ru.stroesku.kmm.presentation.ui.features.schedule.flow

import ru.stroesku.kmm.presentation.ui.features.schedule.ScheduleDay
import ru.dtk.lib.architecture.mvi.DtkBaseAction

sealed class ScheduleAction : DtkBaseAction {
    object GetDays : ScheduleAction()
    data class ChangeState(val isActive: Boolean, val day: ScheduleDay) : ScheduleAction()
}