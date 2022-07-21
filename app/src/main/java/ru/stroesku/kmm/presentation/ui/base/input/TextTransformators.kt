package ru.stroesku.kmm.presentation.ui.base.input

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText


class PhoneTransformation(private val string: AnnotatedString) {

    fun transform(): TransformedText {
        return TransformedText(
            AnnotatedString(
                if (string.text.isNotEmpty()) {
                    var operatorCode = ""
                    var firstSection = ""
                    var secondSection = ""
                    var thirtySection = ""

                    string.text.forEachIndexed { index, char ->
                        when {
                            index in 0..2 -> operatorCode += char
                            index in 3..5 -> firstSection += char
                            index in 6..7 -> secondSection += char
                            index > 7 -> thirtySection += char
                        }
                    }
                    val operator = if (operatorCode.isNotEmpty()) "($operatorCode) " else ""
                    val first = if (firstSection.isNotEmpty()) "$firstSection - " else ""
                    val second = if (secondSection.isNotEmpty()) "$secondSection - " else ""
                    val thirty = thirtySection.ifEmpty { "" }

                    "$operator$first$second$thirty"
                } else {
                    string.text
                }
            ),
            OffsetMapping.Identity
        )
    }
}
