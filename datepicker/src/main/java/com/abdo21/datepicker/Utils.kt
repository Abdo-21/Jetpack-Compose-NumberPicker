package com.abdo21.datepicker

import java.util.Calendar


internal fun numberOfDays(year: Int, month: Int): Int {
    val calendar = Calendar.getInstance()

    // Set the year and month. The month is zero-based, so subtract 1 from the input month.
    calendar.set(year, month - 1, 1)

    // Use the actual maximum number of days in the month.
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}
