package ru.stroesku.kmm.presentation.ui.features.splash

import com.adeo.kviewmodel.BaseSharedViewModel
import ru.stroesku.kmm.data.local.TokenPreferences
import ru.stroesku.kmm.presentation.ui.features.splash.flow.SplashChange
import ru.stroesku.kmm.presentation.ui.features.splash.flow.SplashEvent
import ru.stroesku.kmm.presentation.ui.features.splash.flow.SplashReducer
import ru.stroesku.kmm.presentation.ui.features.splash.flow.SplashViewState
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class SplashViewModel : BaseSharedViewModel<SplashViewState, SplashChange, SplashEvent>(
    initialState = SplashViewState(
        isIdle = true
    )
), KoinComponent {
    private val preferences: TokenPreferences by inject()
    private val reducer = SplashReducer()
    override fun obtainEvent(viewEvent: SplashEvent) {
        merge(
            buildFlowCheckAuthorization(viewEvent)
        )
            .scan(viewStates().value) { state, change -> reducer(state, change) }
            .onEach { viewState = it }
            .launchIn(viewModelScope)
    }

    private fun buildFlowCheckAuthorization(action: SplashEvent): Flow<SplashChange> {
        return flowOf(action)
            .filterIsInstance<SplashEvent.CheckAuthorization>()
            .flatMapConcat { checkAuthorization() }
    }

    private fun checkAuthorization(): Flow<SplashChange> {
        return flow {
            val isAuth = (preferences.getCurrentToken().isNotEmpty())
            emit(if (isAuth) SplashChange.ToMainScreen else SplashChange.ToSelectAuth)
        }
    }
}