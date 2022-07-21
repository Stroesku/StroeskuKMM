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
import ru.stroesku.kmm.presentation.ui.base.input.PhoneTransformation
import ru.stroesku.kmm.R
import ru.stroesku.kmm.presentation.ui.theme.StrTheme.strColors
import ru.stroesku.kmm.presentation.ui.theme.StrTheme.strTypography


@Composable
fun InputPhoneWithTitle(
    modifier: Modifier,
    title: String = stringResource(R.string.phone_number),
    phoneCode: String = stringResource(R.string.phone_code_7),
    onValueChange: (String) -> Unit = {},
) {
    InputTitleText(
        modifier = modifier,
        title = title,
        onValueChange = { onValueChange.invoke("$phoneCode$it") },
        leadingIcon = { Text(text = phoneCode, style = strTypography.normal16) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        visualTransformation = { PhoneTransformation(it).transform() },
    )
}


@Composable
fun InputTitleText(
    title: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    value: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column(modifier = modifier) {
        Text(text = title, style = strTypography.body1)
        Spacer(modifier = Modifier.height(4.dp))
        BaseInputText(
            onValueChange = onValueChange,
            value = value,
            leadingIcon = leadingIcon,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation
        )
    }
}

@Composable
fun BaseInputText(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit,
    shape: Shape = RoundedCornerShape(20.dp),
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        backgroundColor = strColors.secondaryBackground,
        unfocusedBorderColor = strColors.primaryBackground,
        focusedBorderColor = strColors.secondaryTextColor,
        cursorColor = strColors.secondaryTextColor
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
        textStyle = strTypography.normal16
    )
}