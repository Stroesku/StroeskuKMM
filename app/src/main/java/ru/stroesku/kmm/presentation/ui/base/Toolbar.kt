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
import ru.stroesku.kmm.presentation.ui.extension.isNotEmptyOrBlank
import ru.stroesku.kmm.R
import ru.stroesku.kmm.presentation.ui.theme.StrTheme
import ru.stroesku.kmm.presentation.ui.theme.StrTheme.strColors
import ru.stroesku.kmm.presentation.ui.theme.StrTheme.strTypography

@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    icon: Int = R.drawable.ic_arrow_left,
    iconTint: Color = strColors.primaryButton,
    title: String = "",
    contentDescription: String = "Back",
    backgroundColor: Color = strColors.primaryBackground,
    elevation: Dp = 0.dp,
    onBackPress: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Row(horizontalArrangement = Arrangement.Start, modifier = modifier.fillMaxWidth()) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = contentDescription,
                    tint = iconTint,
                    modifier = modifier.clickable {
                        onBackPress.invoke()
                    }
                )

                if (title.isNotEmptyOrBlank()) Text(
                    text = title,
                    style = strTypography.medium18,
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
    StrTheme {
        Toolbar()
    }
}