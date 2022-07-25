package ru.stroesku.kmm.presentation.ui.features.profile.update_phone

import com.adeo.kviewmodel.BaseSharedViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.stroesku.kmm.data.repositories.UserRepository
import ru.stroesku.kmm.domain.exception.ResponseException
import ru.stroesku.kmm.domain.param.SignParam
import ru.stroesku.kmm.presentation.ui.features.profile.update_phone.flow.UpdatePhoneAction
import ru.stroesku.kmm.presentation.ui.features.profile.update_phone.flow.UpdatePhoneEvent
import ru.stroesku.kmm.presentation.ui.features.profile.update_phone.flow.UpdatePhoneReducer
import ru.stroesku.kmm.presentation.ui.features.profile.update_phone.flow.UpdatePhoneViewState

@FlowPreview
@ExperimentalCoroutinesApi
class UpdatePhoneViewModel :
    BaseSharedViewModel<UpdatePhoneViewState, UpdatePhoneAction, UpdatePhoneEvent>(
        UpdatePhoneViewState(isIdle = true)
    ),
    KoinComponent {
    private val repository: UserRepository by inject()
    private val reducer = UpdatePhoneReducer()

    override fun obtainEvent(viewEvent: UpdatePhoneEvent) {
        merge(
            buildFlowCloseErrorDialog(viewEvent),
            buildFlowSetPhoneSubState(viewEvent),
            buildFlowInputPhone(viewEvent),
            buildFlowSendPhone(viewEvent),
            buildFlowSendCode(viewEvent)
        )
            .catch {
                when (it) {
                    is ResponseException -> emit(
                        UpdatePhoneAction.ShowError(
                            it.model.userMessage
                        )
                    )
                }
            }
            .scan(viewStates().value) { state, change -> reducer(state, change) }
            .filter { !it.isIdle }
            .distinctUntilChanged()
            .onEach { viewState = it }
            .launchIn(viewModelScope)
    }

    private fun buildFlowInputPhone(action: UpdatePhoneEvent): Flow<UpdatePhoneAction> {
        return flowOf(action)
            .filterIsInstance<UpdatePhoneEvent.InputPhone>()
            .flatMapConcat {
                //todo need to make a proper validator later.
                flowOf(UpdatePhoneAction.SetValid(it.phone, it.phone.length == 12) as UpdatePhoneAction)
            }
    }

    private fun buildFlowCloseErrorDialog(action: UpdatePhoneEvent): Flow<UpdatePhoneAction> {
        return flowOf(action)
            .filterIsInstance<UpdatePhoneEvent.CloseErrorDialog>()
            .flatMapConcat { flowOf(UpdatePhoneAction.HideError as UpdatePhoneAction) }
    }

    private fun buildFlowSetPhoneSubState(action: UpdatePhoneEvent): Flow<UpdatePhoneAction> {
        return flowOf(action)
            .filterIsInstance<UpdatePhoneEvent.SetPhoneSubState>()
            .flatMapConcat { flowOf(UpdatePhoneAction.SetPhoneSubState as UpdatePhoneAction) }
    }

    private fun buildFlowSendPhone(action: UpdatePhoneEvent): Flow<UpdatePhoneAction> {
        return flowOf(action)
            .filterIsInstance<UpdatePhoneEvent.SendPhone>()
            .flatMapConcat {
                repository.sendPhoneUpdatePhone(it.phone)
                    .map { UpdatePhoneAction.SetCodeSubState as UpdatePhoneAction }
            }
    }

    private fun buildFlowSendCode(action: UpdatePhoneEvent): Flow<UpdatePhoneAction> {
        return flowOf(action)
            .filterIsInstance<UpdatePhoneEvent.SendCode>()
            .flatMapConcat {
                repository.sendSmsCodeUpdatePhone(SignParam(phone = it.phone, code = it.code))
                    .map { UpdatePhoneAction.CompleteStep as UpdatePhoneAction }
            }
    }
}