package ru.stroesku.kmm.presentation.ui.features.sign.`in`

import com.adeo.kviewmodel.BaseSharedViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.stroesku.kmm.data.repositories.UserRepository
import ru.stroesku.kmm.domain.exception.ResponseException
import ru.stroesku.kmm.domain.param.SignParam
import ru.stroesku.kmm.presentation.ui.features.sign.`in`.flow.SignInAction
import ru.stroesku.kmm.presentation.ui.features.sign.`in`.flow.SignInChange
import ru.stroesku.kmm.presentation.ui.features.sign.`in`.flow.SignInReducer
import ru.stroesku.kmm.presentation.ui.features.sign.`in`.flow.SignInViewState

@FlowPreview
@ExperimentalCoroutinesApi
class SignInViewModel :
    BaseSharedViewModel<SignInViewState, SignInChange, SignInAction>(SignInViewState(isIdle = true)),
    KoinComponent {
    private val repository: UserRepository by inject()
    private val reducer = SignInReducer()

    override fun obtainEvent(viewEvent: SignInAction) {
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
                        SignInChange.ShowError(
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

    private fun buildFlowInputPhone(action: SignInAction): Flow<SignInChange> {
        return flowOf(action)
            .filterIsInstance<SignInAction.InputPhone>()
            .flatMapConcat {
                //todo need to make a proper validator later.
                flowOf(SignInChange.SetValid(it.phone, it.phone.length == 12) as SignInChange)
            }
    }

    private fun buildFlowCloseErrorDialog(action: SignInAction): Flow<SignInChange> {
        return flowOf(action)
            .filterIsInstance<SignInAction.CloseErrorDialog>()
            .flatMapConcat { flowOf(SignInChange.HideError as SignInChange) }
    }

    private fun buildFlowSetPhoneSubState(action: SignInAction): Flow<SignInChange> {
        return flowOf(action)
            .filterIsInstance<SignInAction.SetPhoneSubState>()
            .flatMapConcat { flowOf(SignInChange.SetPhoneSubState as SignInChange) }
    }

    private fun buildFlowSendPhone(action: SignInAction): Flow<SignInChange> {
        return flowOf(action)
            .filterIsInstance<SignInAction.SendPhone>()
            .flatMapConcat {
                repository.sendPhoneSignIn(it.phone)
                    .map { SignInChange.SetCodeSubState as SignInChange }
            }
    }

    private fun buildFlowSendCode(action: SignInAction): Flow<SignInChange> {
        return flowOf(action)
            .filterIsInstance<SignInAction.SendCode>()
            .flatMapConcat {
                repository.sendSmsCodeSignIn(SignParam(phone = it.phone, code = it.code))
                    .map { SignInChange.CompleteStep as SignInChange }
            }
    }
}