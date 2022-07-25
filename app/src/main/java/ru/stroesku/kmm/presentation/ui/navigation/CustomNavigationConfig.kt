package ru.stroesku.kmm.presentation.ui.navigation

import androidx.compose.runtime.Composable
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.CustomNavConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.MultiStackBuilder
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabsNavModel

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