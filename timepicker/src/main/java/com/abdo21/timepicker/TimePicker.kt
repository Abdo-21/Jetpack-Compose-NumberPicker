package com.abdo21.timepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.abdo21.numberpicker.NumberPicker
import com.abdo21.numberpicker.PickerDividerStyle
import com.abdo21.numberpicker.PickerTextStyle
import com.abdo21.numberpicker.StringPicker
import java.util.Calendar

enum class TimeMode { AM, PM }


data class AMPMTime(
    val hour: Int,
    val minute: Int,
    val timeMode: TimeMode
) {
    companion object {
        val NOW = run {
            val calendar = Calendar.getInstance()
            val currentHour = calendar.get(Calendar.HOUR)
            val currentMinute = calendar.get(Calendar.MINUTE)
            val mode = calendar.get(Calendar.AM_PM)
            AMPMTime(
                hour = currentHour,
                minute = currentMinute,
                timeMode = if (mode == Calendar.AM) TimeMode.AM else TimeMode.PM
            )
        }

        val Zero = AMPMTime(
            hour = 0,
            minute = 0,
            timeMode = TimeMode.AM
        )
    }
}

data class H24Time(
    val hour: Int,
    val minute: Int,
) {
    companion object {
        val NOW = run {
            val calendar = Calendar.getInstance()
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = calendar.get(Calendar.MINUTE)
            H24Time(
                hour = currentHour,
                minute = currentMinute
            )
        }

        val Zero = H24Time(
            hour = 0,
            minute = 0
        )
    }
}

/**
 * A AM/PM Hour Time picker.
 *
 * @param initialTime The initial time to display.
 * @param onValueChanged Callback invoked when the time changes.
 * @param modifier Modifier for customizing layout.
 * @param dividerStyle Style for dividers between items.
 * @param itemSpacing Spacing between the picker items.
 * @param selectedTextStyle Text style for the selected time.
 * @param unselectedTextStyle Text style for unselected times.
 */
@Composable
fun TimePicker(
    initialTime: AMPMTime,
    onValueChanged: (selectedTime: AMPMTime) -> Unit,
    modifier: Modifier = Modifier,
    dividerStyle: PickerDividerStyle = PickerDividerStyle.Default,
    itemSpacing: Dp = 10.dp,
    selectedTextStyle: PickerTextStyle = PickerTextStyle.Default,
    unselectedTextStyle: PickerTextStyle = PickerTextStyle.Default
) {
    var time by remember {
        mutableStateOf(initialTime)
    }

    val hourRange = 1..12
    val minuteRange = 0..59
    val modes = listOf("AM", "PM")

    PickerRow(
        modifier = modifier,
        itemSpacing = itemSpacing.toPx().toInt()
    ) {
        NumberPicker(
            values = hourRange,
            initialIndex = time.hour - 1,
            onValueChanged = { selectedIndex ->
                time = time.copy(hour = hourRange.first + selectedIndex)
            },
            dividerStyle = dividerStyle,
            selectedTextStyle = selectedTextStyle,
            unselectedTextStyle = unselectedTextStyle,
        )

        NumberPicker(
            values = minuteRange,
            initialIndex = time.minute,
            onValueChanged = { selectedIndex ->
                time = time.copy(minute = minuteRange.first + selectedIndex)
            },
            dividerStyle = dividerStyle,
            selectedTextStyle = selectedTextStyle,
            unselectedTextStyle = unselectedTextStyle,
        )

        StringPicker(
            values = modes,
            initialIndex = time.timeMode.ordinal,
            onValueChanged = { selectedIndex ->
                time = time.copy(timeMode = if (modes[selectedIndex] == "AM") TimeMode.AM else TimeMode.PM)
            },
            dividerStyle = dividerStyle,
            selectedTextStyle = selectedTextStyle,
            unselectedTextStyle = unselectedTextStyle,
        )
    }

    LaunchedEffect(time) {
        onValueChanged(time)
    }
}

/**
 * A 24 Hour Time picker.
 *
 * @param initialTime The initial time to display.
 * @param onValueChanged Callback invoked when the time changes.
 * @param modifier Modifier for customizing layout.
 * @param dividerStyle Style for dividers between items.
 * @param itemSpacing Spacing between the picker items.
 * @param selectedTextStyle Text style for the selected time.
 * @param unselectedTextStyle Text style for unselected times.
 */
@Composable
fun TimePicker(
    initialTime: H24Time,
    onValueChanged: (selectedTime: H24Time) -> Unit,
    modifier: Modifier = Modifier,
    dividerStyle: PickerDividerStyle = PickerDividerStyle.Default,
    itemSpacing: Dp = 10.dp,
    selectedTextStyle: PickerTextStyle = PickerTextStyle.Default,
    unselectedTextStyle: PickerTextStyle = PickerTextStyle.Default
) {
    var time by remember {
        mutableStateOf(initialTime)
    }

    val hourRange = 0..23
    val minuteRange = 0..59

    PickerRow(
        modifier = modifier,
        itemSpacing = itemSpacing.toPx().toInt()
    ) {
        NumberPicker(
            values = hourRange,
            initialIndex = time.hour - 1,
            onValueChanged = { selectedIndex ->
                time = time.copy(hour = hourRange.first + selectedIndex)
            },
            dividerStyle = dividerStyle,
            selectedTextStyle = selectedTextStyle,
            unselectedTextStyle = unselectedTextStyle,
        )

        NumberPicker(
            values = minuteRange,
            initialIndex = time.minute,
            onValueChanged = { selectedIndex ->
                time = time.copy(minute = minuteRange.first + selectedIndex)
            },
            dividerStyle = dividerStyle,
            selectedTextStyle = selectedTextStyle,
            unselectedTextStyle = unselectedTextStyle,
        )
    }

    LaunchedEffect(time) {
        onValueChanged(time)
    }
}


@Preview(showBackground = true)
@Composable
private fun AMPMTimePickerPreview() {
    TimePicker(
        initialTime = AMPMTime.NOW,
        modifier = Modifier
            .background(color = Color.LightGray)
            .size(width = 340.dp, height = 300.dp),
        onValueChanged = { time ->

        },
    )
}

@Preview(showBackground = true)
@Composable
private fun H24TimePickerPreview() {
    TimePicker(
        initialTime = H24Time.NOW,
        modifier = Modifier
            .background(color = Color.LightGray)
            .size(width = 340.dp, height = 300.dp),
        onValueChanged = { selectedTime ->

        },
    )
}