package ru.stroesku.kmm.presentation.ui.features.schedule

import com.adeo.kviewmodel.BaseSharedViewModel
import ru.stroesku.kmm.domain.exception.ResponseException
import ru.stroesku.kmm.presentation.ui.features.schedule.flow.ScheduleAction
import ru.stroesku.kmm.presentation.ui.features.schedule.flow.ScheduleChange
import ru.stroesku.kmm.presentation.ui.features.schedule.flow.ScheduleReducer
import ru.stroesku.kmm.presentation.ui.features.schedule.flow.ScheduleViewState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import timber.log.Timber
import java.util.*

@FlowPreview
class ScheduleViewModel :
    BaseSharedViewModel<ScheduleViewState, ScheduleChange, ScheduleAction>(ScheduleViewState(isIdle = true)),
    KoinComponent {

    val list = listOf(
        ScheduleDay(1, "Понедельник", Date(), Date(), true),
        ScheduleDay(2, "Вторник", Date(), Date(), true),
        ScheduleDay(3, "Среда", Date(), Date(), true),
        ScheduleDay(4, "Четверг", Date(), Date(), true),
        ScheduleDay(5, "Пятница", Date(), Date(), true),
        ScheduleDay(6, "Суббота", Date(), Date(), false),
        ScheduleDay(7, "Воскресенье", Date(), Date(), false),
    )

    val reducer = ScheduleReducer()

    override fun obtainEvent(viewEvent: ScheduleAction) {
        merge(
            buildFlowGetDays(viewEvent),
            buildFlowChangeState(viewEvent),
        )
            .catch {
                when (it) {
                    is ResponseException -> emit(
                        ScheduleChange.Error(
                            it.model.userMessage
                        )
                    )
                }
            }
            .scan(viewStates().value) { state, change -> reducer(state, change) }
            .filter { !it.isIdle }
            .onEach {
                Timber.e("state :$it")
                viewState = it
            }
            .launchIn(viewModelScope)
    }

    private fun buildFlowGetDays(viewEvent: ScheduleAction): Flow<ScheduleChange> {
        return flowOf(viewEvent)
            .filterIsInstance<ScheduleAction.GetDays>()
            .flatMapConcat {
                flowOf(ScheduleChange.SetList(list))
            }
    }

    private fun buildFlowChangeState(viewEvent: ScheduleAction): Flow<ScheduleChange> {
        return flowOf(viewEvent)
            .filterIsInstance<ScheduleAction.ChangeState>()
            .flatMapConcat {
                val item = it.day
                val isActive = it.isActive
                val findDay = list.find { it.id == item.id }
                findDay?.let { it.isActive = isActive }
                Timber.e("buildFlow ${list.toString()}")
                flowOf(ScheduleChange.SetList(list))
            }
    }
}