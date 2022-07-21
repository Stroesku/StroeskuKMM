package ru.stroesku.kmm.presentation.ui.features.sign.up

import com.adeo.kviewmodel.BaseSharedViewModel
import ru.stroesku.kmm.data.repositories.UserRepository
import ru.stroesku.kmm.domain.exception.ResponseException
import ru.stroesku.kmm.presentation.ui.extension.isNotEmptyOrBlank
import ru.stroesku.kmm.presentation.ui.features.sign.up.flow.SignUpAction
import ru.stroesku.kmm.presentation.ui.features.sign.up.flow.SignUpChange
import ru.stroesku.kmm.presentation.ui.features.sign.up.flow.SignUpReducer
import ru.stroesku.kmm.presentation.ui.features.sign.up.flow.SignUpViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@FlowPreview
@ExperimentalCoroutinesApi
class SignUpViewModel :
    BaseSharedViewModel<SignUpViewState, SignUpChange, SignUpAction>(SignUpViewState(isIdle = true)),
    KoinComponent {
    private val reducer = SignUpReducer()
    private val repository: UserRepository by inject()

    override fun obtainEvent(viewEvent: SignUpAction) {
        merge(
            buildFlowCloseErrorDialog(viewEvent),
            buildFlowSetPhoneSubState(viewEvent),
            buildFlowInputPhone(viewEvent),
            buildFlowSendPhone(viewEvent),
            buildFlowSendCode(viewEvent)
        )
            .onStart { emit(SignUpChange.Loading) }
            .catch {
                when (it) {
                    is ResponseException -> emit(
                        SignUpChange.ShowError(
                            it.model.userMessage
                        )
                    )
                }
            }
            .scan(viewStates().value) { state, change ->
                reducer(state, change)
            }
            .filter { !it.isIdle }
            .distinctUntilChanged()
            .onEach { viewState = it }
            .launchIn(viewModelScope)
    }

    private fun buildFlowInputPhone(action: SignUpAction): Flow<SignUpChange> {
        return flowOf(action)
            .filterIsInstance<SignUpAction.Validate>()
            .flatMapConcat {
                //todo need to make a proper validator later.
                val phone: String = it.param.phone.orEmpty().trim()
                val firstName: String = it.param.firstName.orEmpty().trim()
                val lastName: String = it.param.lastName.orEmpty().trim()

                val isValid =
                    phone.length == 12 && firstName.isNotEmptyOrBlank() && lastName.isNotEmptyOrBlank()
                flowOf(
                    SignUpChange.SetValid(
                        phone = phone,
                        firstName = firstName,
                        lastName = lastName,
                        isValid = isValid
                    ) as SignUpChange
                )
            }
    }

    private fun buildFlowCloseErrorDialog(action: SignUpAction): Flow<SignUpChange> {
        return flowOf(action)
            .filterIsInstance<SignUpAction.CloseErrorDialog>()
            .flatMapConcat { flowOf(SignUpChange.HideError as SignUpChange) }
    }

    private fun buildFlowSetPhoneSubState(action: SignUpAction): Flow<SignUpChange> {
        return flowOf(action)
            .filterIsInstance<SignUpAction.SetPhoneSubState>()
            .flatMapConcat { flowOf(SignUpChange.SetPhoneSubState as SignUpChange) }
    }

    private fun buildFlowSendPhone(action: SignUpAction): Flow<SignUpChange> {
        return flowOf(action)
            .filterIsInstance<SignUpAction.SendPhone>()
            .flatMapConcat {
                repository.sendPhoneSignUp(it.phone)
                    .map { SignUpChange.SetCodeSubState as SignUpChange }
            }
    }

    private fun buildFlowSendCode(action: SignUpAction): Flow<SignUpChange> {
        return flowOf(action)
            .filterIsInstance<SignUpAction.SendCode>()
            .flatMapConcat {
                repository.sendSmsCodeSignUp(it.param)
                    .map { SignUpChange.CompleteStep as SignUpChange }
            }
    }
}