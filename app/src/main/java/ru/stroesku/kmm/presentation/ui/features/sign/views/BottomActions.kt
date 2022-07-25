package ru.stroesku.kmm.presentation.ui.features.sign.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.stroesku.kmm.presentation.ui.base.PrimaryButton
import ru.stroesku.kmm.R
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseColors
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseTypography
import ru.stroesku.kmm.presentation.ui.utils.SpannableUtils

@Composable
fun BottomActions(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isValid: Boolean = false,
    action: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        val annotatedString = SpannableUtils.amendSpannableString(
            commonText = stringResource(id = R.string.sign_in_user_agreement_1),
            selectText = stringResource(id = R.string.sign_in_user_agreement_2),
            color = baseColors.secondaryTextColor
        )

        Text(
            text = annotatedString,
            modifier = Modifier.clickable {},
            style = baseTypography.body1,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton(
            enabled = isValid,
            text = stringResource(R.string.get_code),
            onClick = action,
            isLoading = isLoading
        )
    }
}