package ru.stroesku.kmm.presentation.ui.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.stroesku.kmm.presentation.ui.base.drawer.LocalDrawerCoroutineScope
import ru.stroesku.kmm.presentation.ui.base.drawer.LocalDrawerState
import kotlinx.coroutines.launch
import ru.stroesku.kmm.R
import ru.stroesku.kmm.presentation.ui.extension.isNotEmptyOrBlank
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseColors
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseTypography

@Composable
fun MainToolbar(
    title: String = "",
    backgroundColor: Color = baseColors.primaryBackground,
    iconTint: Color = baseColors.primaryButton,
) {
    val drawerState = LocalDrawerState.current
    val scope = LocalDrawerCoroutineScope.current
    Toolbar(
        icon = R.drawable.ic_burger,
        title = title,
        backgroundColor = backgroundColor,
        iconTint = iconTint,
    ) {
        scope.launch { drawerState.open() }
    }
}

@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    icon: Int = R.drawable.ic_arrow_left,
    iconTint: Color = baseColors.primaryButton,
    title: String = "",
    contentDescription: String = "Back",
    backgroundColor: Color = baseColors.primaryBackground,
    elevation: Dp = 0.dp,
    onClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Row(horizontalArrangement = Arrangement.Start, modifier = modifier.fillMaxWidth()) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = contentDescription,
                    tint = iconTint,
                    modifier = modifier.clickable {
                        onClick.invoke()
                    }
                )

                if (title.isNotEmptyOrBlank()) Text(
                    text = title,
                    style = baseTypography.medium18,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 32.dp),
                    textAlign = TextAlign.Center
                )
            }
        },
        backgroundColor = backgroundColor,
        elevation = elevation,
    )
}

@Preview
@Composable
fun Preview() {
    BaseTheme {
        Toolbar()
    }
}