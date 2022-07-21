package ru.stroesku.kmm.presentation.ui.features.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.stroesku.kmm.presentation.ui.extension.isNotZero
import ru.stroesku.kmm.R
import ru.stroesku.kmm.presentation.ui.base.Toolbar
import ru.stroesku.kmm.presentation.ui.theme.StrTheme
import ru.stroesku.kmm.presentation.ui.theme.StrTheme.strColors
import ru.stroesku.kmm.presentation.ui.theme.StrTheme.strShapes
import ru.stroesku.kmm.presentation.ui.theme.StrTheme.strTypography


@Composable
fun MainScreen() {
    Scaffold(drawerContent = { MainDrawer() }) {
        Toolbar(Modifier.padding(it), icon = R.drawable.ic_burger)
    }
}

@Composable
fun MainDrawer() {
    Surface(
        color = strColors.thirtyBackground,
        modifier = Modifier.fillMaxSize()
    ) {
        Column() {
            DrawerHeader()
            DrawerMenuItems(modifier = Modifier.padding(top = 32.dp))
        }
    }
}

@Composable
fun DrawerHeader() {
    Row(Modifier.padding(top = 30.dp, start = 16.dp, end = 16.dp)) {
        Image(
            modifier = Modifier
                .size(56.dp)
                .clip(strShapes.roundDefault),
            painter = painterResource(id = R.drawable.test_user), contentDescription = "user",
            contentScale = ContentScale.Crop
        )
        Column(Modifier.padding(start = 16.dp)) {
            Text(
                text = "Константин Константинопольский ",
                style = strTypography.normal20,
                color = strColors.primaryInvertTextColor
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = "48 уборок",
                style = strTypography.normal14,
                color = strColors.hintTextColor
            )
        }
    }
}

@Composable
fun DrawerMenuItems(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        MenuItem("Доступные заказы")
        MenuItem("Мои заказы", 4)
        MenuItem("Архив заказов")
    }
    Divider(color = strColors.dividerColor)
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
    text: String,
    badgeCount: Int = 0,
    onClick: () -> Unit = {}
) {
    Divider(color = strColors.dividerColor)
    Button(
        modifier = Modifier
            .fillMaxSize(),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = strColors.thirtyBackground)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = text,
                style = strTypography.normal16,
                color = strColors.primaryInvertTextColor,
            )
            if (badgeCount.isNotZero()) {
                Badge(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .alignBy(LastBaseline),
                    backgroundColor = strColors.notificationColor
                ) {
                    Text(
                        text = "+$badgeCount",
                        style = strTypography.normal16,
                        color = strColors.primaryInvertTextColor
                    )
                }

            }
        }
    }

}

fun MainContent(modifier: Modifier) {}

@Preview(showBackground = true, heightDp = 800, widthDp = 350)
@Composable
fun Preview() {
    StrTheme {
        MainDrawer()
    }
}


@Composable
fun SelfScreen() {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = "SelfScreen",
        textAlign = TextAlign.Center
    )
}

@Composable
fun ArchScreen() {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = "SelfScreen",
        textAlign = TextAlign.Center
    )
}