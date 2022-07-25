package ru.stroesku.kmm.presentation.ui.base.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.alexgladkov.odyssey.compose.base.TabNavigator
import ru.alexgladkov.odyssey.compose.controllers.MultiStackRootController
import ru.alexgladkov.odyssey.compose.controllers.TabNavigationModel
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.stroesku.kmm.R
import ru.stroesku.kmm.presentation.ui.extension.isNotZero
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseColors
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseShapes
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseTypography

@Composable
fun MainDrawerScreen() {
    val rootController = LocalRootController.current as MultiStackRootController
    val tabItem = rootController.stackChangeObserver.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scaffoldState = rememberScaffoldState(drawerState)

    CompositionLocalProvider(
        LocalCurrentTab provides tabItem.value,
        LocalDrawerState provides drawerState,
        LocalDrawerCoroutineScope provides coroutineScope
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            drawerContent = {
                MainDrawerContent(
                    items = rootController.tabItems, onItemClick = {
                        coroutineScope.launch {
                            rootController.switchTab(it)
                            scaffoldState.drawerState.close()
                        }
                    })
            }
        ) {
            TabNavigator(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(), null, tabItem.value
            )
        }

        rootController.tabsNavModel.launchedEffect.invoke()
    }
}

@Composable
fun MainDrawerContent(
    items: List<TabNavigationModel>,
    onItemClick: (TabNavigationModel) -> Unit
) {
    Surface(
        color = baseColors.thirtyBackground,
        modifier = Modifier.fillMaxSize()
    ) {
        Column() {
            MainDrawerHeader()
            MainDrawerMenuItems(
                modifier = Modifier.padding(top = 32.dp),
                items = items,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun MainDrawerHeader() {
    Row(Modifier.padding(top = 30.dp, start = 16.dp, end = 16.dp)) {
        Image(
            modifier = Modifier
                .size(56.dp)
                .clip(baseShapes.roundDefault),
            painter = painterResource(id = R.drawable.test_user), contentDescription = "user",
            contentScale = ContentScale.Crop
        )
        Column(Modifier.padding(start = 16.dp)) {
            Text(
                text = "Константин Константинопольский ",
                style = baseTypography.normal20,
                color = baseColors.primaryInvertTextColor
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = "48 уборок",
                style = baseTypography.normal14,
                color = baseColors.hintTextColor
            )
        }
    }
}

@Composable
fun MainDrawerMenuItems(
    modifier: Modifier = Modifier,
    items: List<TabNavigationModel>,
    onItemClick: (TabNavigationModel) -> Unit
) {

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        items.forEach { MenuItem(it, onClick = { onItemClick(it) }) }
    }
    Divider(color = baseColors.dividerColor)
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        painter = painterResource(id = R.drawable.ic_logo),
        contentDescription = stringResource(R.string.content_desc_logo)
    )
}

@Composable
fun MenuItem(
    tabNavigationModel: TabNavigationModel,
    badgeCount: Int = 0,
    onClick: (TabNavigationModel) -> Unit = {}
) {

    Divider(color = baseColors.dividerColor)
    Button(
        modifier = Modifier
            .fillMaxSize(),
        onClick = { onClick(tabNavigationModel) },
        colors =
        ButtonDefaults.buttonColors(
            backgroundColor = baseColors.thirtyBackground
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = tabNavigationModel.tabInfo.tabItem.configuration.title,
                style = baseTypography.normal16, color = if (tabNavigationModel == LocalCurrentTab.current) baseColors.secondaryTextColor else baseColors.primaryInvertTextColor,
            )
            if (badgeCount.isNotZero()) {
                Badge(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .alignBy(LastBaseline),
                    backgroundColor = baseColors.notificationColor
                ) {
                    Text(
                        text = "+$badgeCount",
                        style = baseTypography.normal16,
                        color = baseColors.primaryInvertTextColor
                    )
                }
            }
        }
    }
}

private val LocalCurrentTab = compositionLocalOf<TabNavigationModel> {
    error("current tab in undefined")
}

val LocalDrawerState = compositionLocalOf<DrawerState> {
    error("current tab in undefined")
}

val LocalDrawerCoroutineScope = compositionLocalOf<CoroutineScope> {
    error("current tab in undefined")
}