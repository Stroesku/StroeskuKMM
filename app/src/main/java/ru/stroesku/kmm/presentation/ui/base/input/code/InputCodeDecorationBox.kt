package ru.stroesku.kmm.presentation.ui.base.input.code

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.stroesku.kmm.presentation.ui.extension.getOrEmpty
import ru.stroesku.kmm.presentation.ui.theme.StrTheme.strColors
import ru.stroesku.kmm.presentation.ui.theme.StrTheme.strShapes
import ru.stroesku.kmm.presentation.ui.theme.StrTheme.strTypography

@Composable
fun InputCodeDecorationBox(
    modifier: Modifier = Modifier,
    value: String,
    length: Int,
    onCompleteInput: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
    ) {
        val array = value.toCharArray()
        val list = arrayOfNulls<String>(length)

        list.forEachIndexed { index, _ ->
            list[index] = array.getOrEmpty(index)
        }

        repeat(list.size) {
            val s = list[it].orEmpty()
            if (s.isNotEmpty() && it == length - 1)
                onCompleteInput.invoke(array.joinToString(""))

            Cell(text = s, modifier = modifier)
        }
    }
}

@Composable
private fun Cell(modifier: Modifier, text: String) {
    Card(
        shape = strShapes.roundDefault,
        modifier = modifier.width(48.dp),
        border = BorderStroke(width = 0.5.dp, color = strColors.borderColor)
    ) {
        Text(
            text = text,
            modifier = modifier
                .width(48.dp)
                .padding(top = 4.dp),
            style = strTypography.h1,
            textAlign = TextAlign.Center
        )
    }
}
