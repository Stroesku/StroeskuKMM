@file:OptIn(FlowPreview::class)

package ru.stroesku.kmm.presentation.ui.features.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import ru.stroesku.kmm.R
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel

import ru.stroesku.kmm.presentation.ui.base.view.camera.PhotoScreen
import kotlinx.coroutines.FlowPreview
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.stroesku.kmm.domain.param.ProfileUpdParam
import ru.stroesku.kmm.presentation.ui.base.*
import ru.stroesku.kmm.presentation.ui.base.input.DateInput
import ru.stroesku.kmm.presentation.ui.base.input.TextInput
import ru.stroesku.kmm.presentation.ui.features.profile.flow.ProfileAction
import ru.stroesku.kmm.presentation.ui.features.profile.flow.ProfileEvent
import ru.stroesku.kmm.presentation.ui.features.profile.flow.ProfileViewState
import ru.stroesku.kmm.presentation.ui.navigation.MainTabs
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseColors
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseTypography

@FlowPreview
@Composable
fun ProfileScreen() {
    StoredViewModel(factory = { ProfileViewModel() }) { viewModel ->
        val viewState = viewModel.viewStates().observeAsState().value
        val viewActions = viewModel.viewActions().observeAsState().value ?: ProfileAction.Empty

        render(
            viewState = viewState,
            viewAction = viewActions,
            viewModel = viewModel,
        )

        LaunchedEffect(key1 = true, block = { viewModel.obtainEvent(ProfileEvent.GetProfile) })
    }
}

@Composable
fun render(
    viewState: ProfileViewState,
    viewAction: ProfileAction,
    viewModel: ProfileViewModel
) {
    val param = remember { ProfileUpdParam() }
    fun validateEvent(p: ProfileUpdParam) = viewModel.obtainEvent(ProfileEvent.Validate(p))
    fun saveChangesEvent(p: ProfileUpdParam) = viewModel.obtainEvent(ProfileEvent.UpdateProfile(p))
    fun openPhotoScreen() = viewModel.obtainEvent(ProfileEvent.OpenPhotoScreen)

    Column() {
        ProfileToolbar()
        ProfileContent(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            state = viewState,
            validateEvent = { validateEvent(it) },
            onSaveChanges = { saveChangesEvent(it) },
            openPhotoScreen = { openPhotoScreen() },
            param = param
        )
    }

    observeActions(
        action = viewAction,
        param = param,
        validateEvent = { validateEvent(it) })

}

@Composable
fun observeActions(
    action: ProfileAction,
    param: ProfileUpdParam,
    validateEvent: (ProfileUpdParam) -> Unit,
) {
    when (action) {
        is ProfileAction.SuccessUpdate -> SuccessDialog(stringResource(R.string.success_update),
            onClose = {
                with(param) {
                    firstName = null
                    lastname = null
                    dateOfBirth = null
                    email = null
                    phone = null
                    photo = null
                }

                validateEvent(param)
            }
        )
        is ProfileAction.Error -> ErrorDialog(message = action.message)
        is ProfileAction.OpenPhotoScreen -> {
            PhotoScreen {
                param.photo = it
                validateEvent(param)
            }
        }
        else -> {}
    }
}


@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    state: ProfileViewState,
    validateEvent: (ProfileUpdParam) -> Unit,
    onSaveChanges: (ProfileUpdParam) -> Unit,
    openPhotoScreen: () -> Unit,
    param: ProfileUpdParam
) {
    Scaffold(bottomBar = {
        PrimaryButton(
            modifier = modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.save),
            onClick = { onSaveChanges(param) },
            enabled = state.isValid,
            isLoading = state.isLoading
        )
    }) {
        Surface(modifier = Modifier.fillMaxSize(), color = baseColors.primaryBackground) {
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
                    .padding(top = 8.dp)
            ) {
                ImageHeader(state, param, openPhotoScreen)

                TextInput(
                    modifier = Modifier.padding(top = 8.dp),
                    title = stringResource(id = R.string.first_name),
                    value = state.model?.firstName.orEmpty(),
                    onValueChange = { s ->
                        param.firstName = s
                        validateEvent(param)
                    }
                )

                TextInput(
                    modifier = Modifier.padding(top = 8.dp),
                    title = stringResource(id = R.string.last_name),
                    value = state.model?.lastName.orEmpty(),
                    onValueChange = { s ->
                        param.lastname = s
                        validateEvent(param)
                    }
                )

                DateInput(
                    modifier = Modifier.padding(top = 8.dp),
                    title = stringResource(id = R.string.date_of_birth),
                    value = state.model?.birthDate.orEmpty(),
                    onValueChange = { s ->
                        param.dateOfBirth = s
                        validateEvent(param)
                    }
                )

                TextInput(
                    modifier = Modifier.padding(top = 8.dp),
                    title = stringResource(id = R.string.email),
                    value = state.model?.email.orEmpty(),
                    onValueChange = { s ->
                        param.email = s
                        validateEvent(param)
                    }
                )

                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(id = R.string.phone_number),
                    style = baseTypography.body1
                )

                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = state.model?.phone.orEmpty(), style = baseTypography.normal20
                    )
                    val rootController = LocalRootController.current
                    Text(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .clickable {
                                rootController.launch(MainTabs.UpdatePhone.name)
                            },
                        text = stringResource(id = R.string.change),
                        style = baseTypography.body1,
                        color = baseColors.secondaryTextColor
                    )
                }
            }
        }
    }
}

@Composable
fun ImageHeader(state: ProfileViewState, param: ProfileUpdParam, onChangePhoto: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        val photo = param.photo ?: state.model?.photo
        BaseAsyncImage(photo)

        Text(
            modifier = Modifier
                .padding(top = 4.dp)
                .clickable { onChangePhoto.invoke() },
            text = stringResource(R.string.upload_new_photo),
            style = baseTypography.body1,
            color = baseColors.secondaryTextColor
        )
    }
}

@Composable
fun ProfileToolbar() {
    MainToolbar(title = stringResource(id = R.string.profile))
}

@FlowPreview
@Preview(showBackground = true)
@Composable
fun Preview() {
    BaseTheme { ProfileScreen() }
}