package com.abdo21.datepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.abdo21.core.PickerRow
import com.abdo21.core.toPx
import com.abdo21.numberpicker.NumberPicker
import com.abdo21.numberpicker.PickerDividerStyle
import com.abdo21.numberpicker.PickerTextStyle

data class Date(
    val year: Int,
    val month: Int,
    val day: Int,
) {
    companion object {
        val Default = currentDate()
    }
}

@Composable
fun DatePicker(
    onValueChanged: (year: Int, month: Int, day: Int) -> Unit,
    modifier: Modifier = Modifier,
    initialDate: Date = Date.Default,
    dividerStyle: PickerDividerStyle = PickerDividerStyle.Default,
    itemSpacing: Dp = 10.dp,
    selectedTextStyle: PickerTextStyle = PickerTextStyle.Default,
    unselectedTextStyle: PickerTextStyle = PickerTextStyle.Default,
    minYear: Int = 1987,
    maxYear: Int = 2100
) {
    var year by remember {
        mutableIntStateOf(initialDate.year)
    }

    var month by remember {
        mutableIntStateOf(initialDate.month)
    }

    var day = initialDate.day

    val yearRange = minYear..maxYear
    val monthRange = 1..12

    val dayRange by remember(year, month) {
        mutableStateOf(1..numberOfDays(year, month))
    }

    PickerRow(
        modifier = modifier,
        itemSpacing = itemSpacing.toPx().toInt()
    ) {
        NumberPicker(
            values = yearRange,
            initialIndex = year - minYear,
            onValueChanged = { selectedIndex ->
                year = yearRange.first + selectedIndex
            },
            dividerStyle = dividerStyle,
            selectedTextStyle = selectedTextStyle,
            unselectedTextStyle = unselectedTextStyle,
        )

        NumberPicker(
            values = monthRange,
            initialIndex = month,
            onValueChanged = { selectedIndex ->
                month = monthRange.first + selectedIndex
            },
            dividerStyle = dividerStyle,
            selectedTextStyle = selectedTextStyle,
            unselectedTextStyle = unselectedTextStyle,
        )

        NumberPicker(
            values = dayRange,
            initialIndex = day-1,
            onValueChanged = { selectedIndex ->
                day = dayRange.first + selectedIndex
            },
            dividerStyle = dividerStyle,
            selectedTextStyle = selectedTextStyle,
            unselectedTextStyle = unselectedTextStyle,
        )
    }

    LaunchedEffect(key1 = year, key2 = month, key3 = day) {
        onValueChanged(year, month, day)
    }
}


@Preview(showBackground = true)
@Composable
private fun DatePickerPreview() {
    DatePicker(
        modifier = Modifier
            .background(color = Color.LightGray)
            .size(width = 340.dp, height = 300.dp),
        onValueChanged = { _, _, _ ->

        }
    )
}