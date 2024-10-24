package com.abdo21.timepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.abdo21.numberpicker.StringPicker

enum class TimeMode { AM, PM }

@Composable
fun TimePicker(
    onValueChanged: (hour: Int, minute: Int, timeMode: TimeMode) -> Unit,
    modifier: Modifier = Modifier,
    dividerStyle: PickerDividerStyle = PickerDividerStyle.Default,
    itemSpacing: Dp = 10.dp,
    selectedTextStyle: PickerTextStyle = PickerTextStyle.Default,
    unselectedTextStyle: PickerTextStyle = PickerTextStyle.Default
) {
    var hour = 0
    var minute = 0
    var timeMode = TimeMode.AM

    val hourRange = 1..12
    val minuteRange = 0..59
    val modes = listOf("AM", "PM")

    PickerRow(
        modifier = modifier,
        itemSpacing = itemSpacing.toPx().toInt()
    ) {
        NumberPicker(
            values = hourRange,
            onValueChanged = { selectedIndex ->
                hour = hourRange.first + selectedIndex
            },
            dividerStyle = dividerStyle,
            selectedTextStyle = selectedTextStyle,
            unselectedTextStyle = unselectedTextStyle,
        )

        NumberPicker(
            values = minuteRange,
            onValueChanged = { selectedIndex ->
                minute = minuteRange.first + selectedIndex
            },
            dividerStyle = dividerStyle,
            selectedTextStyle = selectedTextStyle,
            unselectedTextStyle = unselectedTextStyle,
        )

        StringPicker(
            values = modes,
            onValueChanged = { selectedIndex ->
                timeMode = if (modes[selectedIndex] == "AM") TimeMode.AM else TimeMode.PM
            },
            dividerStyle = dividerStyle,
            selectedTextStyle = selectedTextStyle,
            unselectedTextStyle = unselectedTextStyle,
        )
    }

    LaunchedEffect(key1 = hour, key2 = minute, key3 = timeMode) {
        onValueChanged(hour, minute, timeMode)
    }
}

@Preview(showBackground = true)
@Composable
private fun TimePickerPreview() {
    TimePicker(
        modifier = Modifier
            .background(color = Color.LightGray)
            .size(width = 340.dp, height = 300.dp),
        onValueChanged = { _, _, _ ->

        }
    )
}