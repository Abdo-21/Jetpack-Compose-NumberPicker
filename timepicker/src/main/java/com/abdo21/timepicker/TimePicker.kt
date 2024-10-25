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

sealed class Time(
    val hour: Int,
    val minute: Int,
    val timeMode: TimeMode?
) {
    abstract fun copy(
        hour: Int = this.hour,
        minute: Int = this.minute,
        timeMode: TimeMode? = this.timeMode
    ) : Time
}

class AMPMTime(
    hour: Int,
    minute: Int,
    timeMode: TimeMode?
) : Time(hour, minute, timeMode) {

    override fun copy(
        hour: Int,
        minute: Int,
        timeMode: TimeMode?
    ) = AMPMTime(
        hour = hour,
        minute = minute,
        timeMode = timeMode
    )

    companion object {
        val Default = run {
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
    }
}

class H24Time(
    hour: Int,
    minute: Int,
) : Time(hour, minute, null) {

    override fun copy(
        hour: Int,
        minute: Int,
        timeMode: TimeMode?
    ) = H24Time(
        hour = hour,
        minute = minute
    )

    companion object {
        val Default = run {
            val calendar = Calendar.getInstance()
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = calendar.get(Calendar.MINUTE)
            H24Time(
                hour = currentHour,
                minute = currentMinute
            )
        }
    }
}

/**
 * A composable for selecting time (hours and minutes).
 *
 * @param onValueChanged Callback invoked when the time changes.
 * @param modifier Modifier for customizing layout.
 * @param initialTime The initial time to display.
 * @param dividerStyle Style for dividers between items.
 * @param itemSpacing Spacing between the picker items.
 * @param selectedTextStyle Text style for the selected time.
 * @param unselectedTextStyle Text style for unselected times.
 */
@Composable
fun TimePicker(
    onValueChanged: (selectedTime: Time) -> Unit,
    modifier: Modifier = Modifier,
    is24Hour: Boolean = false,
    initialTime: Time = if (is24Hour) H24Time.Default else AMPMTime.Default,
    dividerStyle: PickerDividerStyle = PickerDividerStyle.Default,
    itemSpacing: Dp = 10.dp,
    selectedTextStyle: PickerTextStyle = PickerTextStyle.Default,
    unselectedTextStyle: PickerTextStyle = PickerTextStyle.Default
) = if (is24Hour) {
    H24TimePicker(
        onValueChanged = onValueChanged,
        modifier = modifier,
        initialTime = initialTime as H24Time,
        dividerStyle = dividerStyle,
        itemSpacing = itemSpacing,
        selectedTextStyle = selectedTextStyle,
        unselectedTextStyle = unselectedTextStyle
    )
} else {
    AMPMTimePicker(
        onValueChanged = onValueChanged,
        modifier = modifier,
        initialTime = initialTime as AMPMTime,
        dividerStyle = dividerStyle,
        itemSpacing = itemSpacing,
        selectedTextStyle = selectedTextStyle,
        unselectedTextStyle = unselectedTextStyle
    )
}

@Composable
private fun AMPMTimePicker(
    onValueChanged: (selectedTime: Time) -> Unit,
    modifier: Modifier = Modifier,
    initialTime: Time = AMPMTime.Default,
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
            initialIndex = time.timeMode!!.ordinal,
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

@Composable
private fun H24TimePicker(
    onValueChanged: (selectedTime: Time) -> Unit,
    modifier: Modifier = Modifier,
    initialTime: Time = H24Time.Default,
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
private fun TimePickerPreview() {
    TimePicker(
        modifier = Modifier
            .background(color = Color.LightGray)
            .size(width = 340.dp, height = 300.dp),
        onValueChanged = { selectedTime ->

        },
        is24Hour = true
    )
}