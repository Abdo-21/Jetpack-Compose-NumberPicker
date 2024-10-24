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
import com.abdo21.numberpicker.NumberPicker
import com.abdo21.numberpicker.PickerDividerStyle
import com.abdo21.numberpicker.PickerRow
import com.abdo21.numberpicker.PickerTextStyle
import com.abdo21.numberpicker.toPx


@Composable
fun DatePicker(
    onValueChanged: (year: Int, month: Int, day: Int) -> Unit,
    modifier: Modifier = Modifier,
    dividerStyle: PickerDividerStyle = PickerDividerStyle.Default,
    itemSpacing: Dp = 10.dp,
    selectedTextStyle: PickerTextStyle = PickerTextStyle.Default,
    unselectedTextStyle: PickerTextStyle = PickerTextStyle.Default,
    minYear: Int = 1987,
    maxYear: Int = 2100
) {
    var year by remember {
        mutableIntStateOf(0)
    }

    var month by remember {
        mutableIntStateOf(0)
    }

    var day = 0

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
            onValueChanged = { selectedIndex ->
                year = yearRange.first + selectedIndex
            },
            dividerStyle = dividerStyle,
            selectedTextStyle = selectedTextStyle,
            unselectedTextStyle = unselectedTextStyle,
        )

        NumberPicker(
            values = monthRange,
            onValueChanged = { selectedIndex ->
                month = monthRange.first + selectedIndex
            },
            dividerStyle = dividerStyle,
            selectedTextStyle = selectedTextStyle,
            unselectedTextStyle = unselectedTextStyle,
        )

        NumberPicker(
            values = dayRange,
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