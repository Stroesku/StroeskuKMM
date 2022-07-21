package ru.stroesku.kmm.presentation.ui.features.sign.views

import android.os.CountDownTimer
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.stroesku.kmm.presentation.ui.base.Header
import ru.stroesku.kmm.presentation.ui.base.StrText
import ru.stroesku.kmm.presentation.ui.base.input.code.CodeInputField
import ru.stroesku.kmm.R
import ru.stroesku.kmm.presentation.ui.theme.StrTheme
import ru.stroesku.kmm.presentation.ui.theme.StrTheme.strColors
import ru.stroesku.kmm.presentation.ui.utils.timeMinuteSecondsFormat
import java.util.*
import java.util.concurrent.TimeUnit

@Preview(showBackground = true)
@Composable
fun PreviewCodeInput() {
    StrTheme {
        CodeInput(
            title = "Вход",
            phoneHint = "89999999999",
            onRepeatClick = { /*TODO*/ },
            onPhoneChange = { /*TODO*/ },
            onCodeInput = {}
        )
    }
}

@Composable
fun CodeInput(
    modifier: Modifier = Modifier,
    title: String,
    titleHint: String? = null,
    phoneHint: String,
    onRepeatClick: () -> Unit,
    onPhoneChange: () -> Unit,
    onCodeInput: (String) -> Unit
) {
    Column(modifier = modifier) {
        Row {
            Header(text = title)
            if (titleHint != null)
                Header(
                    modifier = Modifier.padding(start = 8.dp),
                    text = titleHint, color = strColors.hintTextColor
                )
        }
        StrText(
            text = stringResource(id = R.string.code_input_send_code, phoneHint),
            modifier = Modifier.padding(top = 8.dp)
        )
        StrText(
            text = stringResource(id = R.string.change),
            color = strColors.secondaryTextColor,
            modifier = Modifier
                .padding(top = 4.dp)
                .clickable { onPhoneChange.invoke() }
        )

        CodeInputField(
            modifier = Modifier
                .padding(top = 24.dp),
            onCompleteInput = onCodeInput
        )

        TimeCounter(onRepeatClick = onRepeatClick)
    }
}

@Composable
fun TimeCounter(seconds: Long = 60, onRepeatClick: () -> Unit) {
    val isFinish = remember { mutableStateOf(false) }
    val time = remember { mutableStateOf(Date(seconds)) }

    val timer = remember {
        object : CountDownTimer(
            TimeUnit.SECONDS.toMillis(seconds),
            TimeUnit.SECONDS.toMillis(1)
        ) {
            override fun onTick(p0: Long) {
                time.value = Date(p0)
            }

            override fun onFinish() {
                isFinish.value = true
            }
        }
    }

    if (isFinish.value) {
        StrText(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
                .clickable {
                    onRepeatClick.invoke()
                    isFinish.value = false
                    timer.start()
                },
            text = stringResource(R.string.repeat_request_code),
            textAlign = TextAlign.Center,
            color = strColors.secondaryTextColor
        )
    } else {
        StrText(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(),
            text = stringResource(
                R.string.repeat_request_code_counter,
                time.value.timeMinuteSecondsFormat()
            ),
            textAlign = TextAlign.Center,
            color = strColors.hintTextColor
        )
    }

    LaunchedEffect(key1 = true, block = { timer.start() })
}
