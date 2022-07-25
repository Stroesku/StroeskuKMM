package ru.stroesku.kmm.presentation.ui.features.orders.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.stroesku.kmm.presentation.ui.features.orders.avaliables.Order
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseColors
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseShapes
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseTypography
import ru.stroesku.kmm.presentation.ui.utils.timeHHMMDayWeekFormat

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderItem(order: Order) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        onClick = { },
        shape = baseShapes.roundDefault,
        elevation = 2.dp
    ) {
        Column() {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "№${order.id}",
                    style = baseTypography.normal14,
                    color = BaseTheme.baseColors.hintTextColor
                )

                Text(
                    text = order.date.timeHHMMDayWeekFormat(),
                    style = baseTypography.normal14,
                    color = baseColors.secondaryTextColor
                )
            }

            Text(
                text = order.address,
                style = baseTypography.medium18,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
            )

            Text(
                text = order.type,
                style = baseTypography.normal14,
                color = baseColors.hintTextColor,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
