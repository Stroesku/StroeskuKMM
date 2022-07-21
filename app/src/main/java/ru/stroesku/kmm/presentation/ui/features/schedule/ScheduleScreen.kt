package ru.stroesku.kmm.presentation.ui.features.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import ru.stroesku.kmm.presentation.ui.features.schedule.ScheduleViewModel
import ru.stroesku.kmm.presentation.ui.features.schedule.flow.ScheduleViewState
import kotlinx.coroutines.FlowPreview
import ru.stroesku.kmm.presentation.ui.base.Toolbar
import ru.stroesku.kmm.presentation.ui.base.VmigDivider
import ru.stroesku.kmm.presentation.ui.features.schedule.flow.ScheduleAction
import ru.stroesku.kmm.presentation.ui.theme.StrTheme
import ru.stroesku.kmm.presentation.ui.theme.StrTheme.strColors
import ru.stroesku.kmm.presentation.ui.theme.StrTheme.strTypography
import ru.stroesku.kmm.presentation.ui.utils.timeHHMMFormat
import timber.log.Timber
import java.util.*

@FlowPreview
@Composable
fun ScheduleScreen() {
    StoredViewModel(factory = { ScheduleViewModel() }) { viewModel ->
        viewModel.viewStates().watch {
            Timber.e("ScheduleScreen $it")
        }
        val state = viewModel.viewStates().observeAsState().value
        Column() {
            Toolbar(title = "Мое расписание")
            ScheduleList(state) { isActive, day ->
                viewModel.obtainEvent(ScheduleAction.ChangeState(isActive, day))
            }
        }
        LaunchedEffect(
            key1 = true,
            block = {
                viewModel.obtainEvent(ScheduleAction.GetDays)
            })
    }
}

@Composable
fun ScheduleList(
    state: ScheduleViewState,
    onActiveItemStateChange: (Boolean, ScheduleDay) -> Unit
) {
    Timber.e("ScheduleList $state")
    if (state.list != null) {
        Timber.e(state.list.toString())
        LazyColumn() {
            items(items = state.list) {
                ScheduleDayItem(it) { onActive, id ->
                    onActiveItemStateChange(onActive, id)
                }
            }
        }
    }
}

@Composable
fun ScheduleDayItem(day: ScheduleDay, onActiveStateChange: (Boolean, ScheduleDay) -> Unit) {
    Timber.e("ScheduleDayItem ${day.title}/${day.isActive}")
    VmigDivider()
    Row(
        Modifier
            .padding(vertical = 28.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = day.title,
            style = strTypography.medium16,
            color = if (day.isActive) strColors.primaryTextColor else strColors.disableButton
        )
        if (day.isActive) TimeRange(Date(), Date())
        Switch(
            checked = day.isActive,
            onCheckedChange = { onActiveStateChange(it, day) }
        )
    }
}

@Composable
fun TimeRange(startTime: Date, endTime: Date) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        TimeItem(time = startTime)
        Text(
            text = "-",
            style = strTypography.medium18,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        TimeItem(time = endTime)
    }
}

@Composable
fun TimeItem(time: Date) {
    Text(
        modifier = Modifier
            .background(
                strColors.secondaryBackground,
                shape = RoundedCornerShape(20.dp)
            )
            .border(1.dp, strColors.borderColor, RoundedCornerShape(20.dp))
            .padding(vertical = 14.dp, horizontal = 16.dp),
        text = time.timeHHMMFormat(),
        color = strColors.secondaryTextColor,
        style = strTypography.normal14
    )
}

@FlowPreview
@Composable
@Preview(showBackground = true)
fun ScheduleScreenPreview() {
    StrTheme {
        ScheduleScreen()
    }
}

data class ScheduleDay(
    val id: Int,
    val title: String,
    val startTime: Date,
    val endTime: Date,
    var isActive: Boolean,
)