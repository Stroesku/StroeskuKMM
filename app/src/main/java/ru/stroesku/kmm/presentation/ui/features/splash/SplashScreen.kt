package ru.stroesku.kmm.presentation.ui.features.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import ru.stroesku.kmm.presentation.ui.features.splash.flow.SplashEvent
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.stroesku.kmm.R
import ru.stroesku.kmm.presentation.ui.features.splash.flow.SplashViewState
import ru.stroesku.kmm.presentation.ui.navigation.RootNavTree
import timber.log.Timber


@Composable
fun SplashScreen() {
    StoredViewModel(factory = { SplashViewModel() }) { viewModel ->

        LaunchedEffect(
            key1 = true,
            block = { viewModel.obtainEvent(SplashEvent.CheckAuthorization) })

        val viewState = viewModel.viewStates().observeAsState().value
        Timber.e(viewState.toString())

        Surface(color = Color(0xFF99FF8E)) {
            SplashContent(Modifier.fillMaxSize(), viewState, LocalRootController.current)
        }
    }
}

@Composable
fun SplashContent(
    modifier: Modifier = Modifier,
    viewState: SplashViewState,
    rootController: RootController
) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        SplashImageSet(Modifier.fillMaxWidth())
    }

    if (viewState.toSelectAuth) rootController.launch(
        RootNavTree.SelectAuth.name,
        launchFlag = LaunchFlag.SingleNewTask
    )

    if (viewState.toMainScreen) rootController.launch(
        RootNavTree.Main.name,
        launchFlag = LaunchFlag.SingleNewTask
    )
}


@Composable
fun SplashImageSet(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}
