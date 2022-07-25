package ru.stroesku.kmm.presentation.ui.base

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseColors

@Composable
fun VmigDivider(modifier: Modifier = Modifier) {
    Divider(modifier = modifier, thickness = 1.dp, color = baseColors.dividerColor)
}