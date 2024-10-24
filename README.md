# Jetpack Compose Pickers Library

This library provides customizable **NumberPicker**, **TimePicker**, and **DatePicker** components for Jetpack Compose. Each picker allows you to easily display and select values with a modern, Compose-friendly API, supporting customization of appearance and behavior.

## Features

- **NumberPicker**: Choose from a list of numeric values with flexible text and divider customization.
- **TimePicker**: Select hours and minutes, supporting both 12-hour and 24-hour formats.
- **DatePicker**: Choose a date from a scrollable calendar view.
- Customizable text styles for selected and unselected items.
- Divider styling between picker items.
- Easy integration with Jetpack Compose's state management.

## Installation

Add the following dependency to your `build.gradle` (Kotlin DSL):

```kotlin
dependencies {
    implementation("com.yourdomain:jetpackpickers:1.0.0")
}
````

##Usage

1. NumberPicker

NumberPicker allows users to scroll and select a numeric value from a list.

Example:

```kotlin
@Composable
fun NumberPickerExample() {
    var selectedIndex by remember { mutableStateOf(0) }

    NumberPicker(
        values = listOf(1, 2, 3, 4, 5),
        selectedIndex = selectedIndex,
        onSelectedIndexChange = { index -> selectedIndex = index },
        selectedTextStyle = PickerTextStyle(fontSize = 20.sp, textColor = Color.Red),
        unselectedTextStyle = PickerTextStyle(fontSize = 14.sp, textColor = Color.Gray)
    )
}
```

2. TimePicker

TimePicker enables users to pick a specific time (hours and minutes). You can choose between 12-hour or 24-hour formats.

Example:

```kotlin
@Composable
fun TimePickerExample() {
    var selectedHour by remember { mutableStateOf(12) }
    var selectedMinute by remember { mutableStateOf(30) }

    TimePicker(
        selectedHour = selectedHour,
        selectedMinute = selectedMinute,
        onTimeChanged = { hour, minute ->
            selectedHour = hour
            selectedMinute = minute
        },
        is24HourFormat = true
    )
}
```

3. DatePicker

DatePicker lets users scroll and select a date (year, month, day) in a scrollable calendar-like format.

Example:

```kotlin
@Composable
fun DatePickerExample() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    DatePicker(
        selectedDate = selectedDate,
        onDateChanged = { date ->
            selectedDate = date
        }
    )
}
```

##Customization

All three pickers (NumberPicker, TimePicker, DatePicker) support the following customizable options:

Text Styles: You can specify different styles for selected and unselected items using PickerTextStyle, allowing you to adjust font size, color, and weight.

Divider Styling: Customize dividers between picker items using PickerDividerStyle to modify their color and thickness.

Example of Custom Styles:

```kotlin
NumberPicker(
    values = (0..10).toList(),
    selectedTextStyle = PickerTextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold, textColor = Color.Blue),
    unselectedTextStyle = PickerTextStyle(fontSize = 16.sp, fontWeight = FontWeight.Light, textColor = Color.Gray),
    dividerStyle = PickerDividerStyle(color = Color.Black, thickness = 1.dp)
)
```

##License

This library is distributed under the MIT License. See the LICENSE file for more information.
