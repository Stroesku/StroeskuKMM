package ru.stroesku.kmm.presentation.ui.theme

import androidx.compose.ui.graphics.Color

data class BaseColors(
    //Background
    val primaryBackground: Color,
    val secondaryBackground: Color,
    val thirtyBackground: Color,
    val fortyBackground: Color,

    //Background state
    val successStateBackground: Color,
    val neutralStateBackground: Color,
    val errorStateBackground: Color,

    //Buttons
    val primaryButton: Color,
    val secondaryButton: Color,
    val disableButton: Color,

    //Text
    val headerTextColor: Color,
    val primaryTextColor: Color,
    val secondaryTextColor: Color,
    val primaryInvertTextColor: Color,
    val hintTextColor: Color,

    //Tint
    val notificationColor: Color,
    val successColor: Color,

    //UI
    val borderColor: Color,
    val dividerColor: Color,
)

