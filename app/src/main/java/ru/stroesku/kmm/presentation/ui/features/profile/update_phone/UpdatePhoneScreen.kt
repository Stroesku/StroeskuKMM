package ru.stroesku.kmm.presentation.ui.features.profile.update_phone

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.stroesku.kmm.R
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import ru.stroesku.kmm.presentation.ui.features.profile.update_phone.flow.UpdatePhoneEvent
import ru.stroesku.kmm.presentation.ui.features.profile.update_phone.flow.UpdatePhoneSubState
import ru.stroesku.kmm.presentation.ui.features.profile.update_phone.flow.UpdatePhoneViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.stroesku.kmm.presentation.ui.base.ErrorDialog
import ru.stroesku.kmm.presentation.ui.base.Header
import ru.stroesku.kmm.presentation.ui.base.PrimaryButton
import ru.stroesku.kmm.presentation.ui.base.Toolbar
import ru.stroesku.kmm.presentation.ui.base.input.PhoneInput
import ru.stroesku.kmm.presentation.ui.features.sign.views.CodeInput
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseColors


@ExperimentalCoroutinesApi
@FlowPreview
@Composable
fun UpdatePhoneScreen() {
    StoredViewModel(factory = { UpdatePhoneViewModel() }) { viewModel ->
        val viewState = viewModel.viewStates().observeAsState().value
        Surface(color = baseColors.primaryBackground) {
            Column() {
                UpdatePhoneToolbar(
                    viewState.subState,
                    setPhoneSubState = { viewModel.obtainEvent(UpdatePhoneEvent.SetPhoneSubState) }
                )

                UpdatePhoneContent(
                    state = viewState,
                    setPhoneSubStateEvent = { viewModel.obtainEvent(UpdatePhoneEvent.SetPhoneSubState) },
                    validatePhoneEvent = { viewModel.obtainEvent(UpdatePhoneEvent.InputPhone(it)) },
                    sendPhoneEvent = { viewModel.obtainEvent(UpdatePhoneEvent.SendPhone(it)) },
                    sendCodeEvent = { phone, code ->
                        viewModel.obtainEvent(UpdatePhoneEvent.SendCode(phone, code))
                    },
                    closeErrorDialog = { viewModel.obtainEvent(UpdatePhoneEvent.CloseErrorDialog) }
                )
            }
        }
    }
}

@Composable
fun UpdatePhoneToolbar(state: UpdatePhoneSubState, setPhoneSubState: () -> Unit) {
    val rootController = LocalRootController.current
    Toolbar() {
        if (state == UpdatePhoneSubState.Phone) {
            rootController.popBackStack()
        } else {
            setPhoneSubState.invoke()
        }
    }
}

@Composable
fun UpdatePhoneContent(
    modifier: Modifier = Modifier,
    state: UpdatePhoneViewState,
    setPhoneSubStateEvent: () -> Unit,
    validatePhoneEvent: (String) -> Unit,
    sendPhoneEvent: (String) -> Unit,
    sendCodeEvent: (String, String) -> Unit,
    closeErrorDialog: () -> Unit,
) {
    if (state.subState == UpdatePhoneSubState.Phone) {
        Column(
            modifier
                .padding(horizontal = 24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            UpdatePhonePhoneInput(onPhoneInput = { validatePhoneEvent(it) })

            PrimaryButton(
                modifier = modifier
                    .padding(bottom = 16.dp),
                enabled = state.isValid,
                text = stringResource(R.string.get_code),
                onClick = { sendPhoneEvent(state.phone) },
                isLoading = state.isLoading
            )
        }
    } else {
        CodeInput(
            modifier = modifier
                .padding(horizontal = 24.dp)
                .fillMaxHeight(),
            title = stringResource(id = R.string.update_phone),
            phoneHint = state.phone,
            onPhoneChange = setPhoneSubStateEvent,
            onCodeInput = { sendCodeEvent(state.phone, it) },
            onRepeatClick = { sendPhoneEvent(state.phone) }
        )
    }
    if (state.errorMessage != null) UpdatePhoneError(
        message = state.errorMessage,
        onClose = closeErrorDialog
    )
    if (state.isComplete) LocalRootController.current.popBackStack()
}

@Composable
fun UpdatePhoneError(message: String, onClose: () -> Unit) {
    ErrorDialog(message = message, onClose = onClose)
}

@Composable
fun UpdatePhonePhoneInput(
    modifier: Modifier = Modifier,
    onPhoneInput: (String) -> Unit
) {
    Column(modifier) {
        Header(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(id = R.string.update_phone)
        )

        PhoneInput(
            modifier = Modifier.padding(top = 24.dp),
            title = stringResource(R.string.phone_input_desc),
            onValueChange = { onPhoneInput.invoke(it) }
        )
    }
}