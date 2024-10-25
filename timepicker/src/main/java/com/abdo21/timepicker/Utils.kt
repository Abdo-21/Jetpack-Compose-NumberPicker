package com.abdo21.timepicker

import java.util.Calendar


internal fun currentTime() : Time {
    val calendar = Calendar.getInstance()

    val currentHour = calendar.get(Calendar.HOUR)
    val currentMinute = calendar.get(Calendar.MINUTE)
    val mode = calendar.get(Calendar.AM_PM)

    return Time(
        hour = currentHour,
        minute = currentMinute,
        timeMode = if (mode == Calendar.AM) TimeMode.AM else TimeMode.PM
    )
}