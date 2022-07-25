package ru.stroesku.kmm.presentation.ui.base

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.stroesku.kmm.R
import ru.stroesku.kmm.presentation.ui.extension.isNotNull
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseColors
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseShapes
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseTypography

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    icon: Int? = null
) {
    BaseButton(
        modifier = modifier.fillMaxWidth(),
        text = text,
        onClick = onClick,
        color = baseColors.primaryButton,
        enabled = enabled,
        isLoading = isLoading,
        icon
    )
}

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    BaseButton(
        modifier = modifier,
        text = text,
        onClick = onClick,
        color = baseColors.secondaryButton,
        enabled
    )
}

@Composable
fun BaseButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    color: Color,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    icon: Int? = null
) {
    Button(
        modifier = modifier.heightIn(min = 56.dp),
        shape = baseShapes.roundDefault,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = color,
            disabledBackgroundColor = baseColors.disableButton
        ),
        onClick = { onClick.invoke() }) {
        if (isLoading) {
            CircularProgressIndicator(
                Modifier.size(24.dp),
                color = baseColors.primaryInvertTextColor
            )
        } else {
            if (icon.isNotNull()) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = text,
                        fontSize = 16.sp,
                        color = baseColors.primaryInvertTextColor,
                        style = baseTypography.button
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = "Next",
                        tint = baseColors.primaryInvertTextColor
                    )
                }
            } else {
                Text(
                    text = text,
                    fontSize = 16.sp,
                    color = baseColors.primaryInvertTextColor,
                    style = baseTypography.button
                )
            }
        }
    }
}