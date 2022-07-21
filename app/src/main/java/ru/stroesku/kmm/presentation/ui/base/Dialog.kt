package ru.stroesku.kmm.presentation.ui.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.stroesku.kmm.presentation.ui.base.StrText
import ru.stroesku.kmm.R
import ru.stroesku.kmm.presentation.ui.theme.StrTheme
import ru.stroesku.kmm.presentation.ui.theme.StrTheme.strTypography

@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier.padding(horizontal = 16.dp),
    message: String,
    onClose: () -> Unit,
    textAction: String = stringResource(R.string.ok),
    isSkipable: Boolean = false
) {
    AlertDialog(
        modifier = modifier,
        title = {
            Image(
                modifier = modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.ic_error),
                contentDescription = stringResource(R.string.content_desc_error),
            )
        },
        text = {
            StrText(
                modifier = modifier.padding(top = 16.dp),
                text = message,
                style = strTypography.medium18,
                textAlign = TextAlign.Center
            )
        },
        shape = StrTheme.strShapes.roundDefault,
        backgroundColor = StrTheme.strColors.primaryBackground,
        onDismissRequest = { if (isSkipable) onClose.invoke() },
        buttons = {
            PrimaryButton(
                modifier = modifier
                    .padding(bottom = 16.dp)
                    .widthIn(min = 100.dp),
                text = textAction,
                onClick = onClose
            )
        })
}