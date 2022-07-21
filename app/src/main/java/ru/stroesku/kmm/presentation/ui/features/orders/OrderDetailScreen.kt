package ru.stroesku.kmm.presentation.ui.features.orders

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.stroesku.kmm.R
import ru.stroesku.kmm.presentation.ui.base.PrimaryButton
import ru.stroesku.kmm.presentation.ui.base.StrText
import ru.stroesku.kmm.presentation.ui.base.VmigDivider
import ru.stroesku.kmm.presentation.ui.features.orders.avaliables.getOrders
import ru.stroesku.kmm.presentation.ui.theme.StrTheme.strColors
import ru.stroesku.kmm.presentation.ui.theme.StrTheme.strTypography
import ru.stroesku.kmm.presentation.ui.utils.timeHHMMDayWeekFormat
import java.util.*

@Composable
fun OrderDetailsScreen() {
    Scaffold(topBar = { OrderDetailsToolbar(24234322) }) {
        Surface(
            color = strColors.fortyBackground,
            modifier = Modifier
                .fillMaxSize()
        ) {
            OrderDetailsContent(
                Modifier
                    .padding(it)
                    .padding(horizontal = 24.dp)
            )
        }
    }
}


@Composable
fun OrderDetailsContent(modifier: Modifier) {
    val order = getOrders().first()
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            AddressInfo(
                modifier = modifier.padding(vertical = 16.dp),
                order.address
            )
            CleanDate(
                modifier = modifier,
                date = Date()
            )

            ClientInfo(
                modifier = modifier,
                name = "Константинопольский Александр Петрович",
                phone = "+7 968 444 44 21"
            )

            Note(
                modifier = modifier,
                note = "Необходима поддерживающая уборка - протереть пыль, заменить полотенца, обновить все гигиенические средства",
            )

        }

        AcceptOrder(modifier)
    }
}

@Composable
fun AcceptOrder(modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = strColors.fortyBackground,
        elevation = 16.dp,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        PrimaryButton(
            modifier = modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            onClick = { },
            text = "Принять заказ",
            icon = R.drawable.ic_arrow_right
        )
    }
}

@Composable
fun AddressInfo(modifier: Modifier, address: String) {
    Column(modifier) {
        Text(text = address, style = strTypography.h1)
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(R.string.on_map),
            style = strTypography.normal14,
            color = strColors.secondaryTextColor
        )
    }
}

@Composable
fun CleanDate(modifier: Modifier, date: Date) {
    VmigDivider()
    Text(
        text = stringResource(R.string.date_clean),
        modifier = modifier.padding(top = 16.dp),
        style = strTypography.medium16
    )

    Text(
        text = date.timeHHMMDayWeekFormat(),
        modifier = modifier.padding(top = 8.dp, bottom = 16.dp),
        style = strTypography.h1,
        color = strColors.secondaryTextColor
    )
}

@Composable
fun ClientInfo(modifier: Modifier, name: String, phone: String) {
    VmigDivider()
    Text(
        text = stringResource(R.string.client),
        modifier = modifier.padding(top = 16.dp),
        style = strTypography.medium16
    )

    StrText(text = name, modifier = modifier.padding(top = 8.dp))

    StrText(
        text = phone,
        modifier = modifier.padding(top = 8.dp, bottom = 16.dp),
        color = strColors.secondaryTextColor
    )
}

@Composable
fun Note(modifier: Modifier, note: String) {
    VmigDivider()
    Text(
        text = stringResource(R.string.notes),
        modifier = modifier.padding(top = 16.dp),
        style = strTypography.medium16
    )

    StrText(
        text = note,
        modifier = modifier.padding(top = 8.dp, bottom = 16.dp)
    )
}

@Composable
fun OrderDetailsToolbar(id: Long) {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = stringResource(id = R.string.content_desc_back),
                    tint = strColors.thirtyBackground,
                    modifier = Modifier.clickable {}
                )

                Text(text = "№$id", style = strTypography.medium18)

                Icon(
                    painter = painterResource(id = R.drawable.ic_phone),
                    contentDescription = stringResource(id = R.string.content_desc_back),
                    tint = strColors.thirtyBackground,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable {}
                )
            }
        },
        backgroundColor = strColors.primaryBackground,
        elevation = 0.dp
    )
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    OrderDetailsToolbar(234324234324)
}