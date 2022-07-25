package ru.stroesku.kmm.presentation.ui.navigation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.stroesku.kmm.presentation.ui.features.select_auth.SelectAuthScreen
import ru.stroesku.kmm.presentation.ui.features.sign.`in`.SignInScreen
import ru.stroesku.kmm.presentation.ui.features.sign.up.SignUpScreen
import ru.stroesku.kmm.presentation.ui.features.splash.SplashScreen


fun RootComposeBuilder.splashScreen() {
    screen(RootNavTree.Splash.name) { SplashScreen() }
}

fun RootComposeBuilder.selectAuthScreen() {
    screen(name = RootNavTree.SelectAuth.name) { SelectAuthScreen() }
}

@FlowPreview
@ExperimentalCoroutinesApi
fun RootComposeBuilder.signInScreen() {
    screen(name = RootNavTree.SignIn.name) { SignInScreen() }
}

@FlowPreview
@ExperimentalCoroutinesApi
fun RootComposeBuilder.signUpScreen() {
    screen(name = RootNavTree.SignUp.name) { SignUpScreen() }
}