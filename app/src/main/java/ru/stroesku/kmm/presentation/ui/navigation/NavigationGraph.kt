package ru.stroesku.kmm.presentation.ui.navigation

import android.content.Context
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.extensions.tab
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.stroesku.kmm.R
import ru.stroesku.kmm.presentation.ui.base.drawer.DrawerItemTab
import ru.stroesku.kmm.presentation.ui.features.orders.avaliables.AvailableScreen
import ru.stroesku.kmm.presentation.ui.features.profile.ProfileScreen
import ru.stroesku.kmm.presentation.ui.features.profile.update_phone.UpdatePhoneScreen

@FlowPreview
@ExperimentalCoroutinesApi
fun RootComposeBuilder.generateRootGraph(context: Context) {
    splashScreen()
    selectAuthScreen()
    signInScreen()
    signUpScreen()

    customNavigation(
        name = RootNavTree.Main.name,
        tabsNavModel = DrawerConfiguration(
            content = { ru.stroesku.kmm.presentation.ui.base.drawer.MainDrawerScreen() }
        )
    ) {
        tab(DrawerItemTab(context.getString(R.string.profile))) {
            screen(name = MainTabs.Profile.name) { ProfileScreen() }
            screen(name = MainTabs.UpdatePhone.name) { UpdatePhoneScreen() }
        }

        tab(DrawerItemTab(context.getString(R.string.available_orders))) {
            screen(name = MainTabs.AvailableOrders.name) { AvailableScreen() }
        }
    }
}