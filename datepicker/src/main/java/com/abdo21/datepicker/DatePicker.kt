package com.abdo21.datepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
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
import com.abdo21.numberpicker.PickerDividerStyle
import com.abdo21.numberpicker.PickerTextStyle
import com.abdo21.numberpicker.VerticalNumberPicker
import java.util.Calendar


private fun numberOfDays(year: Int, month: Int): Int {
    val calendar = Calendar.getInstance()

    // Set the year and month. The month is zero-based, so subtract 1 from the input month.
    calendar.set(year, month - 1, 1)

    // Use the actual maximum number of days in the month.
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}


data class Date(
    val year: Int,
    val month: Int,
    val day: Int,
) {
    companion object {
        val Default = run {
            val calendar = Calendar.getInstance()
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
            Date(
                year = currentYear,
                month = currentMonth,
                day = currentDay
            )
        }
    }
}

/**
 * A composable for selecting a date (year, month, day).
 *
 * @param onValueChanged Callback invoked when the date changes.
 * @param modifier Modifier for customizing layout.
 * @param initialDate The initial date to display.
 * @param dividerStyle Style for dividers between items.
 * @param itemSpacing Spacing between picker items.
 * @param selectedTextStyle Text style for the selected date.
 * @param unselectedTextStyle Text style for unselected dates.
 * @param minYear Minimum year the picker can display.
 * @param maxYear Maximum year the picker can display.
 */
@Composable
fun DatePicker(
    onValueChanged: (date: Date) -> Unit,
    modifier: Modifier = Modifier,
    initialDate: Date = Date.Default,
    dividerStyle: PickerDividerStyle = PickerDividerStyle.Default,
    itemSpacing: Dp = 10.dp,
    selectedTextStyle: PickerTextStyle = PickerTextStyle.Default,
    unselectedTextStyle: PickerTextStyle = PickerTextStyle.Default,
    minYear: Int = 1987,
    maxYear: Int = 2100
) {

    var date by remember {
        mutableStateOf(initialDate)
    }

    val yearRange = minYear..maxYear
    val monthRange = 1..12

    val dayRange by remember(date) {
        derivedStateOf {
            1..numberOfDays(date.year, date.month)
        }
    }

    PickerRow(
        modifier = modifier,
        itemSpacing = itemSpacing.toPx().toInt()
    ) {
        VerticalNumberPicker(
            values = yearRange,
            initialIndex = date.year - minYear,
            onValueChanged = { selectedIndex ->
                date = date.copy(year = yearRange.first + selectedIndex)
            },
            dividerStyle = dividerStyle,
            selectedTextStyle = selectedTextStyle,
            unselectedTextStyle = unselectedTextStyle,
        )

        VerticalNumberPicker(
            values = monthRange,
            initialIndex = date.month,
            onValueChanged = { selectedIndex ->
                date = date.copy(month = monthRange.first + selectedIndex)
            },
            dividerStyle = dividerStyle,
            selectedTextStyle = selectedTextStyle,
            unselectedTextStyle = unselectedTextStyle,
        )

        VerticalNumberPicker(
            values = dayRange,
            initialIndex = date.day-1,
            onValueChanged = { selectedIndex ->
                date = date.copy(day = dayRange.first + selectedIndex)
            },
            dividerStyle = dividerStyle,
            selectedTextStyle = selectedTextStyle,
            unselectedTextStyle = unselectedTextStyle,
        )
    }

    LaunchedEffect(date) {
        onValueChanged(date)
    }
}


@Preview(showBackground = true)
@Composable
private fun DatePickerPreview() {
    DatePicker(
        modifier = Modifier
            .background(color = Color.LightGray)
            .size(width = 340.dp, height = 300.dp),
        onValueChanged = { selectedDate ->

        }
    )
}