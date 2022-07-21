package ru.stroesku.kmm.presentation.ui.features.sign.`in`

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import ru.stroesku.kmm.presentation.ui.base.Header
import ru.stroesku.kmm.presentation.ui.features.sign.`in`.flow.SignInSubState
import ru.stroesku.kmm.presentation.ui.features.sign.`in`.flow.SignInViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.stroesku.kmm.R
import ru.stroesku.kmm.presentation.ui.base.ErrorDialog
import ru.stroesku.kmm.presentation.ui.base.Toolbar
import ru.stroesku.kmm.presentation.ui.base.input.InputPhoneWithTitle
import ru.stroesku.kmm.presentation.ui.features.sign.`in`.flow.SignInAction
import ru.stroesku.kmm.presentation.ui.features.sign.views.BottomActions
import ru.stroesku.kmm.presentation.ui.features.sign.views.CodeInput
import ru.stroesku.kmm.presentation.ui.navigation.RootNavTree
import ru.stroesku.kmm.presentation.ui.theme.StrTheme.strColors
import timber.log.Timber


@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun SignInScreen() {
    StoredViewModel(factory = { SignInViewModel() }) { viewModel ->
        val viewState = viewModel.viewStates().observeAsState().value
        Timber.e("SignInScreen state: $viewState")
        Surface(color = strColors.primaryBackground) {
            Column() {
                SignInToolbar(
                    viewState.subState,
                    setPhoneSubState = { viewModel.obtainEvent(SignInAction.SetPhoneSubState) }
                )

                SignInContent(
                    state = viewState,
                    setPhoneSubStateEvent = { viewModel.obtainEvent(SignInAction.SetPhoneSubState) },
                    validatePhoneEvent = { viewModel.obtainEvent(SignInAction.InputPhone(it)) },
                    sendPhoneEvent = { viewModel.obtainEvent(SignInAction.SendPhone(it)) },
                    sendCodeEvent = { phone, code ->
                        viewModel.obtainEvent(SignInAction.SendCode(phone, code))
                    },
                    closeErrorDialog = { viewModel.obtainEvent(SignInAction.CloseErrorDialog) }
                )
            }
        }
    }
}

@Composable
fun SignInToolbar(state: SignInSubState, setPhoneSubState: () -> Unit) {
    val rootController = LocalRootController.current
    Toolbar() {
        if (state == SignInSubState.Phone) {
            rootController.popBackStack()
        } else {
            setPhoneSubState.invoke()
        }
    }
}

@Composable
fun SignInContent(
    modifier: Modifier = Modifier,
    state: SignInViewState,
    setPhoneSubStateEvent: () -> Unit,
    validatePhoneEvent: (String) -> Unit,
    sendPhoneEvent: (String) -> Unit,
    sendCodeEvent: (String, String) -> Unit,
    closeErrorDialog: () -> Unit,
) {
    if (state.subState == SignInSubState.Phone) {
        Column(
            modifier
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {
            PhoneInput(onPhoneInput = { validatePhoneEvent(it) })
            BottomActions(
                modifier = modifier
                    .padding(bottom = 16.dp)
                    .fillMaxHeight(),
                isValid = state.isValid,
                action = { sendPhoneEvent(state.phone) }
            )
        }
    } else {
        CodeInput(
            modifier = modifier
                .padding(horizontal = 24.dp)
                .fillMaxHeight(),
            title = stringResource(id = R.string.sign_in),
            phoneHint = state.phone,
            onPhoneChange = setPhoneSubStateEvent,
            onCodeInput = { sendCodeEvent(state.phone, it) },
            onRepeatClick = { sendPhoneEvent(state.phone) }
        )
    }
    if (state.errorMessage != null) SignInError(
        message = state.errorMessage,
        onClose = closeErrorDialog
    )
    if (state.isComplete) LocalRootController.current.launch(RootNavTree.Main.name)
}

@Composable
fun SignInError(message: String, onClose: () -> Unit) {
    ErrorDialog(message = message, onClose = onClose)
}

@Composable
fun PhoneInput(
    modifier: Modifier = Modifier,
    onPhoneInput: (String) -> Unit
) {
    Column(modifier) {
        Header(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(id = R.string.sign_in)
        )

        InputPhoneWithTitle(
            modifier = Modifier.padding(top = 24.dp),
            title = stringResource(R.string.phone_input_desc),
            onValueChange = { onPhoneInput.invoke(it) }
        )
    }
}