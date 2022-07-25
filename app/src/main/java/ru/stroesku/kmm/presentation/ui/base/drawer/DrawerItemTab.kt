package ru.stroesku.kmm.presentation.ui.base.drawer

import androidx.compose.runtime.Composable

import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabConfiguration
import ru.alexgladkov.odyssey.compose.navigation.bottom_bar_navigation.TabItem
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseColors
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseTypography

class DrawerItemTab(val title: String) : TabItem() {

    override val configuration: TabConfiguration
        @Composable
        get() {
            return TabConfiguration(
                title = title,
                selectedColor = baseColors.secondaryTextColor,
                unselectedColor = baseColors.primaryTextColor,
                titleStyle = baseTypography.normal16
            )
        }
}