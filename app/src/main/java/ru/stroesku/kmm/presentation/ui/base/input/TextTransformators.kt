package ru.stroesku.kmm.presentation.ui.base.input

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation


class PhoneTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        return phoneFilter(text.text)
    }

    private fun phoneFilter(string: String): TransformedText {
        val out = AnnotatedString(
            if (string.isNotEmpty()) {
                var operatorCode = ""
                var firstSection = ""
                var secondSection = ""
                var thirtySection = ""

                string.forEachIndexed { index, char ->
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
                string
            }
        )

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset < 1) return offset
                if (offset < 3) return offset + 1
                if (offset == 3) return offset + 2
                if (offset in 3..5) return offset + 3
                if (offset in 5..7) return offset + 6
                if (offset in 7..10) return offset + 9
                return 10
            }

            override fun transformedToOriginal(offset: Int) = 0
        }

        return TransformedText(out, numberOffsetTranslator)
    }
}

class DateTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return dateFilter(text)
    }
}

fun dateFilter(text: AnnotatedString): TransformedText {

    val trimmed = if (text.text.length >= 8) text.text.substring(0..7) else text.text
    var out = ""
    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i % 2 == 1 && i < 4) out += "."
    }

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 1) return offset
            if (offset <= 3) return offset + 1
            if (offset <= 8) return offset + 2
            return 10
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 2) return offset
            if (offset <= 5) return offset - 1
            if (offset <= 10) return offset - 2
            return 8
        }
    }

    return TransformedText(AnnotatedString(out), numberOffsetTranslator)
}