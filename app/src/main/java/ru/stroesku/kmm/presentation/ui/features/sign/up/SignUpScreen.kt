package ru.stroesku.kmm.presentation.ui.features.sign.up

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import ru.stroesku.kmm.presentation.ui.base.Header
import ru.stroesku.kmm.presentation.ui.base.Toolbar
import ru.stroesku.kmm.presentation.ui.features.sign.up.flow.SignUpAction
import ru.stroesku.kmm.presentation.ui.features.sign.up.flow.SignUpSubState
import ru.stroesku.kmm.presentation.ui.features.sign.up.flow.SignUpViewState
import ru.stroesku.kmm.presentation.ui.features.sign.views.BottomActions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.stroesku.kmm.R
import ru.stroesku.kmm.domain.param.SignParam
import ru.stroesku.kmm.presentation.ui.base.ErrorDialog
import ru.stroesku.kmm.presentation.ui.base.input.PhoneInput
import ru.stroesku.kmm.presentation.ui.base.input.TextInput
import ru.stroesku.kmm.presentation.ui.features.sign.views.CodeInput
import ru.stroesku.kmm.presentation.ui.navigation.RootNavTree
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseColors
import timber.log.Timber


@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun SignUpScreen() {
    StoredViewModel(factory = { SignUpViewModel() }) { viewModel ->
        val viewState = viewModel.viewStates().observeAsState().value
        Timber.e(viewState.toString())

        Surface(color = baseColors.primaryBackground) {
            Column() {
                SignUpToolbar(viewState.subState) { viewModel.obtainEvent(SignUpAction.SetPhoneSubState) }
                SignUpContent(
                    state = viewState,
                    setPhoneSubStateEvent = { viewModel.obtainEvent(SignUpAction.SetPhoneSubState) },
                    validateEvent = { viewModel.obtainEvent(SignUpAction.Validate(it)) },
                    sendPhoneEvent = { viewModel.obtainEvent(SignUpAction.SendPhone(it)) },
                    sendCodeEvent = { viewModel.obtainEvent(SignUpAction.SendCode(it)) },
                    closeErrorDialog = { viewModel.obtainEvent(SignUpAction.CloseErrorDialog) }
                )
            }
        }
    }
}

@Composable
fun SignUpToolbar(state: SignUpSubState, setPhoneSubState: () -> Unit) {
    val rootController = LocalRootController.current
    Toolbar() {
        if (state == SignUpSubState.Phone) {
            rootController.popBackStack()
        } else {
            setPhoneSubState.invoke()
        }
    }
}

@Composable
fun SignUpContent(
    modifier: Modifier = Modifier,
    state: SignUpViewState,
    validateEvent: (SignParam) -> Unit,
    sendPhoneEvent: (String) -> Unit,
    setPhoneSubStateEvent: () -> Unit,
    sendCodeEvent: (SignParam) -> Unit,
    closeErrorDialog: () -> Unit,
) {
    val userInfo = remember { SignParam() }
    if (state.subState == SignUpSubState.Phone) {
        Column(
            modifier
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {
            InputFields(
                onPhoneInput = {
                    userInfo.phone = it
                    validateEvent(userInfo)
                },
                onFirstNameInput = {
                    userInfo.firstName = it
                    validateEvent(userInfo)
                },
                onLastNameInput = {
                    userInfo.lastName = it
                    validateEvent(userInfo)
                })

            BottomActions(
                modifier = modifier
                    .padding(bottom = 16.dp)
                    .fillMaxHeight(),
                isValid = state.isValid,
                isLoading = state.isLoading,
                action = { sendPhoneEvent(state.phone) }
            )
        }
    } else {
        CodeInput(
            modifier = modifier
                .padding(horizontal = 24.dp)
                .fillMaxHeight(),
            title = stringResource(id = R.string.sign_up),
            titleHint = stringResource(id = R.string.sign_up_step_2),
            phoneHint = state.phone,
            onPhoneChange = { setPhoneSubStateEvent() },
            onCodeInput = {
                userInfo.code = it
                sendCodeEvent(userInfo)
            },
            onRepeatClick = { sendPhoneEvent(state.phone) }
        )
    }
    if (state.errorMessage != null) SignUpError(
        message = state.errorMessage,
        onClose = closeErrorDialog
    )
    if (state.isComplete) LocalRootController.current.launch(RootNavTree.Main.name)
}

@Composable
fun SignUpError(message: String, onClose: () -> Unit) {
    ErrorDialog(message = message, onClose = onClose)
}

@Composable
fun InputFields(
    modifier: Modifier = Modifier,
    onFirstNameInput: (String) -> Unit,
    onLastNameInput: (String) -> Unit,
    onPhoneInput: (String) -> Unit,
) {
    Column(modifier) {
        Header(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(id = R.string.sign_up)
        )
        TextInput(
            modifier = Modifier.padding(top = 24.dp),
            title = stringResource(R.string.first_name),
            onValueChange = { onFirstNameInput(it) })

        TextInput(
            modifier = Modifier.padding(top = 24.dp),
            title = stringResource(R.string.last_name),
            onValueChange = { onLastNameInput(it) })

        PhoneInput(
            modifier = Modifier.padding(top = 8.dp),
            title = stringResource(R.string.phone_number),
            onValueChange = { onPhoneInput(it) }
        )
    }
}