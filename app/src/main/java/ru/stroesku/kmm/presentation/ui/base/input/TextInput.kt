package ru.stroesku.kmm.presentation.ui.base.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.stroesku.kmm.presentation.ui.base.input.DateTransformation
import ru.stroesku.kmm.presentation.ui.base.input.PhoneTransformation
import ru.stroesku.kmm.R
import ru.stroesku.kmm.presentation.ui.extension.isNotEmptyOrBlank
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseColors
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme.baseTypography


@Composable
fun DateInput(
    modifier: Modifier,
    title: String = stringResource(R.string.date_of_birth),
    value: String = "",
    onValueChange: (String) -> Unit = {},
) {
    TextInput(
        modifier = modifier,
        title = title,
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = DateTransformation(),
    )
}

@Composable
fun PhoneInput(
    modifier: Modifier,
    title: String = stringResource(R.string.phone_number),
    phoneCode: String = stringResource(R.string.phone_code_7),
    onValueChange: (String) -> Unit = {},
) {
    TextInput(
        modifier = modifier,
        title = title,
        onValueChange = { onValueChange.invoke("$phoneCode$it") },
        leadingIcon = { Text(text = phoneCode, style = baseTypography.normal16) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        visualTransformation = PhoneTransformation(),
    )
}

@Composable
fun TextInput(
    title: String? = null,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    value: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column(modifier = modifier) {
        title?.let {
            Text(text = it, style = baseTypography.body1)
            Spacer(modifier = Modifier.height(4.dp))
        }

        if (value.isNotEmptyOrBlank()) BaseInputText(
            onValueChange = onValueChange,
            leadingIcon = leadingIcon,
            value = value,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation
        )
        else BaseInputText(
            onValueChange = onValueChange,
            leadingIcon = leadingIcon,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation
        )
    }
}

@Composable
private fun BaseInputText(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit,
    shape: Shape = RoundedCornerShape(20.dp),
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        backgroundColor = baseColors.secondaryBackground,
        unfocusedBorderColor = baseColors.primaryBackground,
        focusedBorderColor = baseColors.secondaryTextColor,
        cursorColor = baseColors.secondaryTextColor
    ),
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    val textState = rememberSaveable { mutableStateOf(value) }
    OutlinedTextField(
        value = textState.value,
        modifier = modifier
            .height(53.dp)
            .fillMaxWidth(),
        onValueChange = {
            textState.value = it
            onValueChange.invoke(it)
        },
        shape = shape,
        colors = colors,
        leadingIcon = leadingIcon,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        textStyle = baseTypography.normal16
    )
}