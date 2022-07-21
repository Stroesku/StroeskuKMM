package ru.stroesku.kmm.presentation.ui.features.orders.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.stroesku.kmm.presentation.ui.features.orders.avaliables.Order
import ru.stroesku.kmm.presentation.ui.theme.StrTheme
import ru.stroesku.kmm.presentation.ui.theme.StrTheme.strColors
import ru.stroesku.kmm.presentation.ui.theme.StrTheme.strShapes
import ru.stroesku.kmm.presentation.ui.theme.StrTheme.strTypography
import ru.stroesku.kmm.presentation.ui.utils.timeHHMMDayWeekFormat

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderItem(order: Order) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        onClick = { },
        shape = strShapes.roundDefault,
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
                    style = strTypography.normal14,
                    color = StrTheme.strColors.hintTextColor
                )

                Text(
                    text = order.date.timeHHMMDayWeekFormat(),
                    style = strTypography.normal14,
                    color = strColors.secondaryTextColor
                )
            }

            Text(
                text = order.address,
                style = strTypography.medium18,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
            )

            Text(
                text = order.type,
                style = strTypography.normal14,
                color = strColors.hintTextColor,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
