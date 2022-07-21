package ru.stroesku.kmm.presentation.ui.features.schedule.flow

import ru.stroesku.kmm.presentation.ui.features.schedule.ScheduleDay

sealed class ScheduleChange {
    object Loading : ScheduleChange()
    data class SetList(val list: List<ScheduleDay>) : ScheduleChange()
    data class UpdateList(val list: List<ScheduleDay>) : ScheduleChange()
    data class Error(val message: String) : ScheduleChange()
}