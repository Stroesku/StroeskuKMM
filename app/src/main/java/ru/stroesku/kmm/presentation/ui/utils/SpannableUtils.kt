package ru.stroesku.kmm.presentation.ui.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

object SpannableUtils {

    fun amendSpannableString(commonText: String, selectText: String, color: Color): AnnotatedString {
        return buildAnnotatedString {
            append(commonText)
            append(" ")
            withStyle(style = SpanStyle(color)) {
                append(selectText)
            }
        }
    }
}