package ru.stroesku.kmm.presentation.ui.features.profile

import com.adeo.kviewmodel.BaseSharedViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.stroesku.kmm.data.repositories.UserRepository
import ru.stroesku.kmm.domain.exception.ResponseException
import ru.stroesku.kmm.domain.param.ProfileUpdParam
import ru.stroesku.kmm.presentation.ui.extension.isNotNull
import ru.stroesku.kmm.presentation.ui.extension.isNotNullOrEmptyOrBlank
import ru.stroesku.kmm.presentation.ui.features.profile.flow.ProfileAction
import ru.stroesku.kmm.presentation.ui.features.profile.flow.ProfileEvent
import ru.stroesku.kmm.presentation.ui.features.profile.flow.ProfileReducer
import ru.stroesku.kmm.presentation.ui.features.profile.flow.ProfileViewState

@FlowPreview
class ProfileViewModel :
    BaseSharedViewModel<ProfileViewState, ProfileAction, ProfileEvent>(ProfileViewState(isEmpty = true)),
    KoinComponent {

    private val reducer = ProfileReducer()
    private val repository: UserRepository by inject()

    override fun obtainEvent(viewEvent: ProfileEvent) {
        merge(
            buildFlowGetProfileInfo(viewEvent),
            buildFlowValidate(viewEvent),
            buildFlowUpdateProfile(viewEvent),
            buildFlowPhotoScreenState(viewEvent),
        )
            .onEach {
                viewAction = when (it) {
                    is ProfileAction.OpenPhotoScreen -> ProfileAction.OpenPhotoScreen
                    is ProfileAction.SuccessUpdate -> ProfileAction.SuccessUpdate(it.newModel)
                    is ProfileAction.Error -> ProfileAction.Error(it.message)
                    else -> ProfileAction.Empty
                }
            }
            .scan(viewStates().value) { state, change -> reducer(state, change) }
            .filter { !it.isEmpty }
            .distinctUntilChanged()
            .onEach { viewState = it }
            .launchIn(viewModelScope)
    }

    private fun buildFlowGetProfileInfo(action: ProfileEvent): Flow<ProfileAction> {
        return flowOf(action)
            .filterIsInstance<ProfileEvent.GetProfile>()
            .flatMapConcat { getProfileInfo() }
    }

    private fun getProfileInfo(): Flow<ProfileAction> {
        return repository.getProfile()
            .map { ProfileAction.SetProfileInfo(it) as ProfileAction }
            .onStart { emit(ProfileAction.Loading) }
            .catch {
                when (it) {
                    is ResponseException -> emit(ProfileAction.Error(it.model.userMessage))
                }
            }
    }

    private fun buildFlowValidate(action: ProfileEvent): Flow<ProfileAction> {
        return flowOf(action)
            .filterIsInstance<ProfileEvent.Validate>()
            .flatMapConcat { validate(it.param) }
    }

    private fun validate(param: ProfileUpdParam): Flow<ProfileAction> {
//        if (param.dateOfBirth?.length == 8) {
//            val date = arrayListOf<Char>()
//            param.dateOfBirth!!.toCharArray().forEachIndexed { index, c ->
//                when (index) {
//                    1 -> {
//                        date.add(c)
//                        date.add('.')
//                    }
//                    3 -> {
//                        date.add(c)
//                        date.add('.')
//                    }
//                    else -> date.add(c)
//                }
//                val dmyDate = date.joinToString("").dateDMYParse()
//                Timber.e(dmyDate.toString())
//            }
//        }
        val isValid = param.run {
            firstName.isNotNullOrEmptyOrBlank() ||
                    lastname.isNotNullOrEmptyOrBlank() ||
                    dateOfBirth?.length == 8 || email.isNotNullOrEmptyOrBlank() ||
                    phone.isNotNullOrEmptyOrBlank() ||
                    photo.isNotNull()
        }
        return flowOf(ProfileAction.ValidParam(param, isValid))
    }

    private fun buildFlowPhotoScreenState(viewEvent: ProfileEvent): Flow<ProfileAction> {
        return flowOf(viewEvent)
            .filterIsInstance<ProfileEvent.OpenPhotoScreen>()
            .flatMapConcat { flowOf(ProfileAction.OpenPhotoScreen) }
    }

    private fun buildFlowUpdateProfile(action: ProfileEvent): Flow<ProfileAction> {
        return flowOf(action)
            .filterIsInstance<ProfileEvent.UpdateProfile>()
            .flatMapConcat { updateProfile(it.param) }
    }

    private fun updateProfile(param: ProfileUpdParam): Flow<ProfileAction> {
        return repository.updateProfile(param)
            .map {
                ProfileAction.SuccessUpdate(it) as ProfileAction
            }
            .onStart { emit(ProfileAction.Loading) }
            .catch {
                when (it) {
                    is ResponseException -> emit(ProfileAction.Error(it.model.userMessage))
                }
            }
            .onCompletion { param.photo?.delete() }
    }

//    private fun buildFlowCloseErrorDialog(action: ProfileEvent): Flow<ProfileAction> {
//        return flowOf(action)
//            .filterIsInstance<ProfileEvent.CloseErrorDialog>()
//            .flatMapConcat { flowOf(ProfileAction.Error(null)) }
//    }
//
//    private fun buildFlowCloseSuccessDialog(action: ProfileEvent): Flow<ProfileAction> {
//        return flowOf(action)
//            .filterIsInstance<ProfileEvent.CloseSuccessDialog>()
//            .flatMapConcat { flowOf(ProfileAction.SuccessDialog(false)) }
//    }
}