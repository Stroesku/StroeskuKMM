package ru.stroesku.kmm.presentation.ui.features.orders.avaliables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.stroesku.kmm.presentation.ui.base.Toolbar
import ru.stroesku.kmm.presentation.ui.features.orders.common.OrdersList
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseColors
import ru.dru.test.ui.post.detail.view.CollapsingLayout
import ru.stroesku.kmm.R
import java.util.*

val EXPANDED_SIZE = 270.dp
val COLLAPSED_SIZE = 56.dp

@Composable
fun AvailableScreen() {
    val scrollState = rememberScrollState(0)

    Surface(color = baseColors.thirtyBackground) {
        Column {
            Toolbar(
                backgroundColor = baseColors.thirtyBackground,
                icon = R.drawable.ic_burger,
                iconTint = baseColors.secondaryBackground
            )
            Calendar { scrollState.value }
            OrdersList(scrollState, stringResource(id = R.string.available_orders), getOrders())
        }
    }
}

@Composable
fun Calendar(scroll: () -> Int) {
    CollapsingLayout(
        scroll = scroll,
        expandedSize = EXPANDED_SIZE,
        collapsedSize = COLLAPSED_SIZE,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_logo),
            colorFilter = ColorFilter.tint(baseColors.secondaryBackground),
            contentDescription = "",
        )
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    AvailableScreen()
}

data class Order(
    val id: Int,
    val date: Date,
    val address: String,
    val type: String
)

fun getOrders(): List<Order> {
    return listOf(
        Order(
            1234567, Date(),
            "Москва, Новодмитровская улица 2к2",
            "Поддерживающая уборка"
        ),
        Order(
            1234567, Date(),
            "Москва, Новодмитровская улица 2к2",
            "Поддерживающая уборка"
        ),
        Order(
            1234567, Date(),
            "Москва, Новодмитровская улица 2к2",
            "Поддерживающая уборка"
        ),
        Order(
            1234567, Date(),
            "Москва, Новодмитровская улица 2к2",
            "Поддерживающая уборка"
        ),
        Order(
            1234567, Date(),
            "Москва, Новодмитровская улица 2к2",
            "Поддерживающая уборка"
        ),
        Order(
            1234567, Date(),
            "Москва, Новодмитровская улица 2к2",
            "Поддерживающая уборка"
        ),
        Order(
            1234567, Date(),
            "Москва, Новодмитровская улица 2к2",
            "Поддерживающая уборка"
        ),
        Order(
            1234567, Date(),
            "Москва, Новодмитровская улица 2к2",
            "Поддерживающая уборка"
        ),
        Order(
            1234567, Date(),
            "Москва, Новодмитровская улица 2к2",
            "Поддерживающая уборка"
        ),
        Order(
            1234567, Date(),
            "Москва, Новодмитровская улица 2к2",
            "Поддерживающая уборка"
        ),
        Order(
            1234567, Date(),
            "Москва, Новодмитровская улица 2к2",
            "Поддерживающая уборка"
        ),
    )
}