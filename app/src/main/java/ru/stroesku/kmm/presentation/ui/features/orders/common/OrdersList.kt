package ru.stroesku.kmm.presentation.ui.features.orders.common

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.stroesku.kmm.presentation.ui.features.orders.avaliables.Order
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseColors
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseTypography

@Composable
fun OrdersList(scroll: ScrollState, title: String, list: List<Order>) {
    Surface(
        color = baseColors.primaryBackground,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column() {
            Text(
                modifier = Modifier.padding(16.dp),
                text = title,
                style = baseTypography.medium16
            )
            Column(modifier = Modifier.verticalScroll(scroll)) {
                list.forEach {
                    OrderItem(it)
                }
            }
        }
    }
}