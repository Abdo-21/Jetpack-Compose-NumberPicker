package com.abdo21.numberpicker

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.takeOrElse
import androidx.compose.ui.util.lerp
import com.abdo21.core.toPx
import kotlin.math.absoluteValue
import kotlin.math.max


data class PickerTextStyle(
    val fontWeight: FontWeight = FontWeight.Normal,
    val textSize: TextUnit = TextUnit.Unspecified,
    val textColor: Color = Color.Unspecified
) {
    companion object {
        val Default = PickerTextStyle()
    }
}

data class PickerDividerStyle(
    val color: Color = Color.Unspecified,
    val thickness: Dp = 1.dp
) {
    companion object {
        val Default = PickerDividerStyle()
    }
}

/**
 * A composable for selecting a string from a list of values, aliened vertically.
 *
 * @param values List of strings to display.
 * @param onValueChanged Callback invoked when the selected item changes, providing the new index.
 * @param modifier Modifier for adjusting the layout.
 * @param initialIndex Index of the item selected initially.
 * @param dividerStyle Style for dividers between items.
 * @param selectedTextStyle Style for the selected item’s text.
 * @param unselectedTextStyle Style for unselected items’ text.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VerticalStringPicker(
    values : List<String>,
    onValueChanged: (selectedIndex: Int) -> Unit,
    modifier: Modifier = Modifier,
    initialIndex: Int = 0,
    dividerStyle: PickerDividerStyle = PickerDividerStyle.Default,
    selectedTextStyle: PickerTextStyle = PickerTextStyle.Default,
    unselectedTextStyle: PickerTextStyle = PickerTextStyle.Default
) {
    val newValues = buildList {
        add("")
        addAll(values)
        add("")
    }

    val maxPages = newValues.count()

    val boundedInitialValueIndex = initialIndex.coerceIn(0, newValues.lastIndex)

    val pagerState = rememberPagerState(
        initialPage = boundedInitialValueIndex,
        pageCount = { maxPages },
    )

    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(10)
    )

    val lineWidth = dividerStyle.thickness.toPx()

    val dividerColor = dividerStyle.color.takeOrElse {
        LocalContentColor.current
    }

    val selectedFontSize = selectedTextStyle.textSize.takeOrElse {
        MaterialTheme.typography.bodyLarge.fontSize
    }

    val unselectedFontSize = unselectedTextStyle.textSize.takeOrElse {
        MaterialTheme.typography.bodyLarge.fontSize
    }

    VerticalPager(
        state = pagerState,
        pageSize = OneTherePageSize,
        flingBehavior = fling,
        beyondBoundsPageCount = 10,
        modifier = modifier
            .drawBehind {
                val y1 = size.height / 3
                val y2 = size.height * 2 / 3

                drawLine(
                    color = dividerColor,
                    start = Offset(0f, y1),
                    end = Offset(size.width, y1),
                    strokeWidth = lineWidth
                )

                drawLine(
                    color = dividerColor,
                    start = Offset(0f, y2),
                    end = Offset(size.width, y2),
                    strokeWidth = lineWidth
                )
            }
    ) { page ->

        val pageOffset = 1-pagerState.getOffsetFractionForPage(page = max(page-1, 0))
            .absoluteValue

        val fontWeight = lerp(
            start = unselectedTextStyle.fontWeight.weight,
            stop = selectedTextStyle.fontWeight.weight,
            fraction = pageOffset.coerceIn(0f, 1f)
        )

        val fontSize = lerp(
            start = unselectedFontSize.value,
            stop = selectedFontSize.value,
            fraction = pageOffset.coerceIn(0f, 1f)
        )

        val isSelectedItem = page - 1 == pagerState.currentPage

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = newValues[page],
                fontWeight = FontWeight(fontWeight),
                fontSize = fontSize.sp,
                color = if (isSelectedItem) selectedTextStyle.textColor else unselectedTextStyle.textColor
            )
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        val index = pagerState.currentPage
        onValueChanged(index)
    }
}

/**
 * A composable for selecting a number from a list of values, aliened vertically.
 *
 * @param values List of numbers to display.
 * @param onValueChanged Callback invoked when the selected item changes, providing the new index.
 * @param modifier Modifier for customizing layout.
 * @param initialIndex Index of the item selected initially.
 * @param dividerStyle Style for dividers between items.
 * @param selectedTextStyle Style for the selected item’s text.
 * @param unselectedTextStyle Style for unselected items’ text.
 */
@Composable
fun <T: Number> VerticalNumberPicker(
    values : List<T>,
    onValueChanged: (selectedIndex: Int) -> Unit,
    modifier: Modifier = Modifier,
    initialIndex: Int = 0,
    dividerStyle: PickerDividerStyle = PickerDividerStyle.Default,
    selectedTextStyle: PickerTextStyle = PickerTextStyle.Default,
    unselectedTextStyle: PickerTextStyle = PickerTextStyle.Default
) = VerticalStringPicker(
    values = values.map { it.toString() },
    modifier = modifier,
    initialIndex = initialIndex,
    dividerStyle = dividerStyle,
    selectedTextStyle = selectedTextStyle,
    unselectedTextStyle = unselectedTextStyle,
    onValueChanged = { selectedIndex ->
        onValueChanged(selectedIndex)
    },
)

/**
 * A composable for selecting a number from a range of values, aliened vertically.
 *
 * @param values List of numbers to display.
 * @param onValueChanged Callback invoked when the selected item changes, providing the new index.
 * @param modifier Modifier for customizing layout.
 * @param initialIndex Index of the item selected initially.
 * @param dividerStyle Style for dividers between items.
 * @param selectedTextStyle Style for the selected item’s text.
 * @param unselectedTextStyle Style for unselected items’ text.
 */
@Composable
fun VerticalNumberPicker(
    values : IntRange,
    onValueChanged: (selectedIndex: Int) -> Unit,
    modifier: Modifier = Modifier,
    initialIndex: Int = 0,
    dividerStyle: PickerDividerStyle = PickerDividerStyle.Default,
    selectedTextStyle: PickerTextStyle = PickerTextStyle.Default,
    unselectedTextStyle: PickerTextStyle = PickerTextStyle.Default
) = VerticalNumberPicker(
    values = values.toList(),
    modifier = modifier,
    initialIndex = initialIndex,
    dividerStyle = dividerStyle,
    selectedTextStyle = selectedTextStyle,
    unselectedTextStyle = unselectedTextStyle,
    onValueChanged = { selectedIndex ->
        onValueChanged(selectedIndex)
    },
)


/**
 * A composable for selecting a string from a list of values aliened horizontally.
 *
 * @param values List of strings to display.
 * @param onValueChanged Callback invoked when the selected item changes, providing the new index.
 * @param modifier Modifier for adjusting the layout.
 * @param initialIndex Index of the item selected initially.
 * @param dividerStyle Style for dividers between items.
 * @param selectedTextStyle Style for the selected item’s text.
 * @param unselectedTextStyle Style for unselected items’ text.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalStringPicker(
    values : List<String>,
    onValueChanged: (selectedIndex: Int) -> Unit,
    modifier: Modifier = Modifier,
    initialIndex: Int = 0,
    dividerStyle: PickerDividerStyle = PickerDividerStyle.Default,
    selectedTextStyle: PickerTextStyle = PickerTextStyle.Default,
    unselectedTextStyle: PickerTextStyle = PickerTextStyle.Default
) {
    val newValues = buildList {
        add("")
        addAll(values)
        add("")
    }

    val maxPages = newValues.count()

    val boundedInitialValueIndex = initialIndex.coerceIn(0, newValues.lastIndex)

    val pagerState = rememberPagerState(
        initialPage = boundedInitialValueIndex,
        pageCount = { maxPages },
    )

    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(10)
    )

    val lineWidth = dividerStyle.thickness.toPx()

    val dividerColor = dividerStyle.color.takeOrElse {
        LocalContentColor.current
    }

    val selectedFontSize = selectedTextStyle.textSize.takeOrElse {
        MaterialTheme.typography.bodyLarge.fontSize
    }

    val unselectedFontSize = unselectedTextStyle.textSize.takeOrElse {
        MaterialTheme.typography.bodyLarge.fontSize
    }

    HorizontalPager(
        state = pagerState,
        pageSize = OneTherePageSize,
        flingBehavior = fling,
        beyondBoundsPageCount = 10,
        modifier = modifier
            .drawBehind {
                val x1 = size.width / 3
                val x2 = size.width * 2 / 3

                drawLine(
                    color = dividerColor,
                    start = Offset(x1, 0f),
                    end = Offset(x1, size.height),
                    strokeWidth = lineWidth
                )

                drawLine(
                    color = dividerColor,
                    start = Offset(x2, 0f),
                    end = Offset(x2, size.height),
                    strokeWidth = lineWidth
                )
            }
    ) { page ->

        val pageOffset = 1-pagerState.getOffsetFractionForPage(page = max(page-1, 0))
            .absoluteValue

        val fontWeight = lerp(
            start = unselectedTextStyle.fontWeight.weight,
            stop = selectedTextStyle.fontWeight.weight,
            fraction = pageOffset.coerceIn(0f, 1f)
        )

        val fontSize = lerp(
            start = unselectedFontSize.value,
            stop = selectedFontSize.value,
            fraction = pageOffset.coerceIn(0f, 1f)
        )

        val isSelectedItem = page - 1 == pagerState.currentPage

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = newValues[page],
                fontWeight = FontWeight(fontWeight),
                fontSize = fontSize.sp,
                color = if (isSelectedItem) selectedTextStyle.textColor else unselectedTextStyle.textColor
            )
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        val index = pagerState.currentPage
        onValueChanged(index)
    }
}

/**
 * A composable for selecting a number from a list of values, aliened horizontally.
 *
 * @param values List of numbers to display.
 * @param onValueChanged Callback invoked when the selected item changes, providing the new index.
 * @param modifier Modifier for customizing layout.
 * @param initialIndex Index of the item selected initially.
 * @param dividerStyle Style for dividers between items.
 * @param selectedTextStyle Style for the selected item’s text.
 * @param unselectedTextStyle Style for unselected items’ text.
 */
@Composable
fun <T: Number> HorizontalNumberPicker(
    values : List<T>,
    onValueChanged: (selectedIndex: Int) -> Unit,
    modifier: Modifier = Modifier,
    initialIndex: Int = 0,
    dividerStyle: PickerDividerStyle = PickerDividerStyle.Default,
    selectedTextStyle: PickerTextStyle = PickerTextStyle.Default,
    unselectedTextStyle: PickerTextStyle = PickerTextStyle.Default
) = HorizontalStringPicker(
    values = values.map { it.toString() },
    modifier = modifier,
    initialIndex = initialIndex,
    dividerStyle = dividerStyle,
    selectedTextStyle = selectedTextStyle,
    unselectedTextStyle = unselectedTextStyle,
    onValueChanged = { selectedIndex ->
        onValueChanged(selectedIndex)
    },
)

/**
 * A composable for selecting a number from a range of values, aliened horizontally.
 *
 * @param values List of numbers to display.
 * @param onValueChanged Callback invoked when the selected item changes, providing the new index.
 * @param modifier Modifier for customizing layout.
 * @param initialIndex Index of the item selected initially.
 * @param dividerStyle Style for dividers between items.
 * @param selectedTextStyle Style for the selected item’s text.
 * @param unselectedTextStyle Style for unselected items’ text.
 */
@Composable
fun HorizontalNumberPicker(
    values : IntRange,
    onValueChanged: (selectedIndex: Int) -> Unit,
    modifier: Modifier = Modifier,
    initialIndex: Int = 0,
    dividerStyle: PickerDividerStyle = PickerDividerStyle.Default,
    selectedTextStyle: PickerTextStyle = PickerTextStyle.Default,
    unselectedTextStyle: PickerTextStyle = PickerTextStyle.Default
) = HorizontalNumberPicker(
    values = values.toList(),
    modifier = modifier,
    initialIndex = initialIndex,
    dividerStyle = dividerStyle,
    selectedTextStyle = selectedTextStyle,
    unselectedTextStyle = unselectedTextStyle,
    onValueChanged = { selectedIndex ->
        onValueChanged(selectedIndex)
    },
)


@ExperimentalFoundationApi
private val OneTherePageSize = object : PageSize {
    override fun Density.calculateMainAxisPageSize(
        availableSpace: Int,
        pageSpacing: Int
    ): Int {
        return (availableSpace - 2*pageSpacing)/3
    }
}

@Preview(showBackground = true)
@Composable
private fun VerticalNumberPickerPreview() {
    val values = 1..10
    VerticalNumberPicker(
        modifier = Modifier
            .size(width = 100.dp, height = 150.dp),
        values = values,
        onValueChanged = {

        },
        selectedTextStyle = PickerTextStyle(
            fontWeight = FontWeight.Bold,
            textSize = 20.sp,
            textColor = Color.Red
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun HorizontalNumberPickerPreview() {
    val values = 1..10
    HorizontalNumberPicker(
        modifier = Modifier
            .size(width = 150.dp, height = 100.dp),
        values = values,
        onValueChanged = {

        },
        selectedTextStyle = PickerTextStyle(
            fontWeight = FontWeight.Bold,
            textSize = 20.sp,
            textColor = Color.Red
        ),
    )
}
