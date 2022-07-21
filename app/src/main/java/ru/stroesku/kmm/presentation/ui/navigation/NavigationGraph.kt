package ru.stroesku.kmm.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.extensions.tab
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.*
import ru.stroesku.kmm.presentation.ui.features.main.DrawerScreen
import ru.stroesku.kmm.presentation.ui.features.main.FeedTab
import ru.stroesku.kmm.presentation.ui.features.main.MainDrawerScreen
import ru.stroesku.kmm.presentation.ui.features.select_auth.SelectAuthScreen
import ru.stroesku.kmm.presentation.ui.features.sign.`in`.SignInScreen
import ru.stroesku.kmm.presentation.ui.features.sign.up.SignUpScreen
import ru.stroesku.kmm.presentation.ui.features.splash.ScreenScreen

@FlowPreview
@ExperimentalCoroutinesApi
fun RootComposeBuilder.generateRootGraph() {
    screen(name = RootNavTree.Splash.name) { ScreenScreen() }
    screen(name = RootNavTree.SelectAuth.name) { SelectAuthScreen() }
    screen(name = RootNavTree.SignIn.name) { SignInScreen() }
    screen(name = RootNavTree.SignUp.name) { SignUpScreen() }

    customNavigation(
        name = RootNavTree.Main.name,
        tabsNavModel = DrawerConfiguration(
            content = { DrawerScreen() }
        )
    ) {
        tab(
            tabItem = FeedTab()
        ) {

        }

    }


}

fun RootComposeBuilder.customNavigation(
    name: String,
    tabsNavModel: TabsNavModel<CustomNavConfiguration>,
    builder: MultiStackBuilder.() -> Unit
) {
    addMultiStack(
        key = name,
        tabsNavModel = tabsNavModel,
        multiStackBuilderModel = MultiStackBuilder(name).apply(builder).build()
    )
}

class DrawerConfiguration(val content: @Composable () -> Unit) :
    TabsNavModel<CustomNavConfiguration>() {
    override val navConfiguration: CustomNavConfiguration
        @Composable
        get() {
            return CustomNavConfiguration(content = content)
        }
}

class TestTab(override val configuration: TabConfiguration) : TabItem()
