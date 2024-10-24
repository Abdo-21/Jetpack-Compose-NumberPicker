package com.abdo21.numberpicker

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp


@Composable
fun Int.toDp() = with(LocalDensity.current) {
    this@toDp.toDp()
}

@Composable
fun Float.toDp() = with(LocalDensity.current) {
    this@toDp.toDp()
}

@Composable
fun Dp.toPx() = with(LocalDensity.current) {
    this@toPx.toPx()
}

@Composable
inline fun PickerRow(
    modifier: Modifier = Modifier,
    itemSpacing : Int,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content,
    ) { measurables, constraints ->

        require(measurables.size == 3) { "You should provide 3 elements" }

        val itemWidth = (constraints.maxWidth - 4* itemSpacing)/3

        val placeables = measurables.map {
            it.measure(
                constraints.copy(
                    minWidth = itemWidth,
                    maxWidth = itemWidth,
                )
            )
        }

        val width = placeables.sumOf { it.width } + (placeables.size + 1)*itemSpacing
        val height = placeables.maxOf { it.height }

        layout(width, height) {
            var xPosition = itemSpacing
            placeables.forEach { placeable ->
                placeable.place(xPosition, 0)
                xPosition += placeable.width + itemSpacing
            }
        }
    }
}
