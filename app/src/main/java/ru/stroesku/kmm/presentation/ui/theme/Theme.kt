package ru.stroesku.kmm.presentation.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.stroesku.kmm.R


@Composable
fun StrTheme(content: @Composable () -> Unit) {
    val colors = StrColors(
        primaryBackground = Color(0xFFE3E3E3),
        secondaryBackground = Color(0xFFFFFFFF),
        thirtyBackground = Color(0xFF282828),
        fortyBackground = Color(0xFFF2F2F2),

        successStateBackground = Color(0xFF95B896),
        neutralStateBackground = Color(0xFFDDDDDD),
        errorStateBackground = Color(0xFFF3DADB),

        primaryButton = Color(0xFF282828),
        secondaryButton = Color(0xFF01A34F),
        disableButton = Color(0xFFABABAB),

        headerTextColor = Color(0xFF282828),
        primaryTextColor = Color(0xFF000000),
        primaryInvertTextColor = Color(0xFFFFFFFF),
        hintTextColor = Color(0xFFABABAB),
        secondaryTextColor = Color(0xFF01A34F),

        notificationColor = Color(0xFFFC020E),
        successColor = Color(0xFF4CAF50),

        borderColor = Color(0xFFE3E3E3),
        dividerColor = Color(0xFFDDDDDD),
    )

    val fontFamilyRubik = FontFamily(
        listOf(
            Font(resId = R.font.rubik_regular, weight = FontWeight.Normal),
            Font(resId = R.font.rubik_medium, weight = FontWeight.Medium),
        )
    )

    val typography = StrTypography(
        button = TextStyle(
            fontSize = 16.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Normal,
            fontFamily = fontFamilyRubik
        ),
        h1 = TextStyle(
            fontSize = 32.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Medium,
            fontFamily = fontFamilyRubik
        ),
        body1 = TextStyle(
            fontSize = 12.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Normal,
            fontFamily = fontFamilyRubik
        ),
        normal14 = TextStyle(
            fontSize = 14.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Normal,
            fontFamily = fontFamilyRubik
        ),
        normal16 = TextStyle(
            fontSize = 16.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Normal,
            fontFamily = fontFamilyRubik
        ),
        medium16 = TextStyle(
            fontSize = 16.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Medium,
            fontFamily = fontFamilyRubik
        ),
        medium18 = TextStyle(
            fontSize = 18.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Medium,
            fontFamily = fontFamilyRubik
        ),
        normal20 = TextStyle(
            fontSize = 20.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Normal,
            fontFamily = fontFamilyRubik
        )
    )

    val shape = StrShapes(roundDefault = RoundedCornerShape(20.dp))

    CompositionLocalProvider(
        LocalColorProvider provides colors,
        LocalTypographyProvider provides typography,
        LocalShapeProvider provides shape,
        content = content
    )
}

object StrTheme {
    val strColors: StrColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColorProvider.current

    val strTypography: StrTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypographyProvider.current

    val strShapes: StrShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapeProvider.current
}

val LocalColorProvider = staticCompositionLocalOf<StrColors> {
    error("No default colors provided")
}

val LocalTypographyProvider = staticCompositionLocalOf<StrTypography> {
    error("No default typography provided")
}

val LocalShapeProvider = staticCompositionLocalOf<StrShapes> {
    error("No default shapes provided")
}