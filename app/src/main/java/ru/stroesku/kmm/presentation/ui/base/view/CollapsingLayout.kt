package ru.dru.test.ui.post.detail.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.util.lerp

@Composable
fun CollapsingLayout(
    scroll: () -> Int,
    expandedSize: Dp,
    collapsedSize: Dp,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    val collapseRange = with(LocalDensity.current) { (expandedSize - collapsedSize).toPx() }
    val collapseFraction = (scroll() / collapseRange).coerceIn(0F, 1F)
    val collapseHeight by animateDpAsState(
        targetValue = max(collapsedSize, expandedSize * (1 - collapseFraction))
    )

    Layout(
        modifier = modifier.height(collapseHeight),
        content = content
    ) { measurables, constraints ->
        check(measurables.size == 1)

        val maxSize = expandedSize.roundToPx()
        val minSize = collapsedSize.roundToPx()
        val width = lerp(maxSize, minSize, collapseFraction)
        val placeable = measurables[0].measure(Constraints.fixed(width, width))

        val y = lerp(minSize, constraints.maxHeight - width, collapseFraction)
        val x = lerp(
            (constraints.maxWidth - width) / 2, // centered when expanded
            constraints.maxWidth - width, // right aligned when collapsed
            collapseFraction
        )
        layout(
            width = constraints.maxWidth,
            height = y + width,
        ) {
            placeable.placeRelative(x, y)
        }
    }
}
