package ru.stroesku.kmm.presentation.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.stroesku.kmm.presentation.ui.features.orders.OrderDetailsScreen
import ru.stroesku.kmm.presentation.ui.features.orders.avaliables.AvailableScreen
import ru.stroesku.kmm.presentation.ui.features.schedule.ScheduleScreen
import ru.stroesku.kmm.presentation.ui.features.sign.`in`.SignInScreen
import ru.stroesku.kmm.presentation.ui.features.sign.up.SignUpScreen
import ru.stroesku.kmm.presentation.ui.features.splash.ScreenScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.extensions.tab
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.*
import ru.stroesku.kmm.presentation.ui.features.select_auth.SelectAuthScreen
import ru.stroesku.kmm.presentation.ui.theme.StrTheme.strColors

@FlowPreview
@ExperimentalCoroutinesApi
fun RootComposeBuilder.generateRootGraph() {

    screen(name = RootNavTree.OrderDetails.name) { OrderDetailsScreen() }
    screen(name = RootNavTree.Schedule.name) { ScheduleScreen() }
    screen(name = RootNavTree.AvailableOrders.name) { AvailableScreen() }
    screen(name = RootNavTree.Splash.name) { ScreenScreen() }
    screen(name = RootNavTree.SelectAuth.name) { SelectAuthScreen() }
    screen(name = RootNavTree.SignIn.name) { SignInScreen() }
    screen(name = RootNavTree.SignUp.name) { SignUpScreen() }

    customNavigation(
        name = RootNavTree.Main.name,
        tabsNavModel = CustomConfiguration()
    ) {
        tab(
            tabItem =
            AvailableTab(
                TabConfiguration(
                    title = Tabs.AvailableOrdersTab.name,
                    null,
                    null,
                    selectedColor = Color.Cyan,
                    unselectedColor = Color.Yellow,
                    titleStyle = null
                )
            )
        ) {

        }

//
//        tab(tabItem = AvailableTab(TabConfiguration(Tabs.SelfOrdersTab.name))) {
//            screen(name = RootNavTree.SelfOrders.name) { SelfScreen() }
//        }
//
//        tab(tabItem = AvailableTab(TabConfiguration(Tabs.ArchiveOrdersTab.name))) {
//            screen(name = RootNavTree.ArchiveOrders.name) { ArchScreen() }
//        }
    }
}

class AvailableTab(override val configuration: TabConfiguration) : TabItem()


class CustomConfiguration : TabsNavModel<CustomNavConfiguration>() {

    override val navConfiguration: CustomNavConfiguration
        @Composable
        get() {
            return CustomNavConfiguration {
                Scaffold(drawerContent = { Text(text = "lsdjkfsldkjf") }) {
                    Text(modifier = Modifier.padding(it), text = "content")
                }
            }
        }
}

class CustomBottomConfiguration : TabsNavModel<BottomNavConfiguration>() {

    override val navConfiguration: BottomNavConfiguration
        @Composable
        get() {
            return BottomNavConfiguration(
                backgroundColor = strColors.thirtyBackground,
                selectedColor = strColors.secondaryTextColor,
                unselectedColor = strColors.borderColor
            )
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