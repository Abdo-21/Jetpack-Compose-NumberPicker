package com.abdo21.numberpicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdo21.datepicker.Date
import com.abdo21.datepicker.DatePicker
import com.abdo21.numberpicker.ui.theme.NumberPickerTheme
import com.abdo21.timepicker.AMPMTime
import com.abdo21.timepicker.H24Time
import com.abdo21.timepicker.TimePicker


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NumberPickerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                    ) {
                        NumberPickerExamplePreview()
                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider()
                        StringPickerExamplePreview()
                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider()
                        //AMPMTimePickerExamplePreview()
                        H24TimePickerExamplePreview()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NumberPickerExamplePreview(modifier: Modifier = Modifier) {
    val values = 1..10

    var selectedValue by remember { mutableIntStateOf(0) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = "$selectedValue")
        Spacer(modifier = Modifier.height(10.dp))
        NumberPicker(
            modifier = Modifier
                .size(width = 100.dp, height = 150.dp)
                .background(color = Color.Cyan),
            values = values,
            onValueChanged = { selectedIndex ->
                selectedValue = values.elementAt(selectedIndex)
            },
            selectedTextStyle = PickerTextStyle(
                fontWeight = FontWeight.Bold,
                textSize = 20.sp,
                textColor = Color.Red
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StringPickerExamplePreview(modifier: Modifier = Modifier) {
    val values = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J")

    var selectedValue by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = selectedValue)
        Spacer(modifier = Modifier.height(10.dp))
        StringPicker(
            modifier = Modifier
                .size(width = 100.dp, height = 150.dp)
                .background(color = Color.Blue),
            values = values,
            onValueChanged = { selectedIndex ->
                selectedValue = values[selectedIndex]
            },
            selectedTextStyle = PickerTextStyle(
                fontWeight = FontWeight.Bold,
                textSize = 40.sp,
                textColor = Color.Green
            ),
            unselectedTextStyle = PickerTextStyle(
                textColor = Color.White
            ),
            dividerStyle = PickerDividerStyle(
                color = Color.Cyan,
                thickness = 5.dp
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun AMPMTimePickerExamplePreview(modifier: Modifier = Modifier) {
    var selectedValue by remember { mutableStateOf(AMPMTime.Zero) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = selectedValue.toString())
        Spacer(modifier = Modifier.height(10.dp))

        TimePicker(
            initialTime = AMPMTime.NOW,
            modifier = Modifier
                .background(color = Color.Green)
                .size(width = 340.dp, height = 300.dp),
            onValueChanged = { selectedTime ->
                selectedValue = selectedTime
            },
            selectedTextStyle = PickerTextStyle(
                fontWeight = FontWeight.Bold,
                textSize = 30.sp,
                textColor = Color.Red
            ),
            unselectedTextStyle = PickerTextStyle(
                textColor = Color.Blue
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun H24TimePickerExamplePreview(modifier: Modifier = Modifier) {
    var selectedValue by remember { mutableStateOf(H24Time.Zero) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = selectedValue.toString())
        Spacer(modifier = Modifier.height(10.dp))

        TimePicker(
            initialTime = H24Time.NOW,
            modifier = Modifier
                .background(color = Color.Green)
                .size(width = 340.dp, height = 300.dp),
            onValueChanged = { selectedTime ->
                selectedValue = selectedTime
            },
            selectedTextStyle = PickerTextStyle(
                fontWeight = FontWeight.Bold,
                textSize = 30.sp,
                textColor = Color.Red
            ),
            unselectedTextStyle = PickerTextStyle(
                textColor = Color.Blue
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DatePickerExamplePreview(modifier: Modifier = Modifier) {
    var selectedValue by remember { mutableStateOf(Date.Default) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = selectedValue.toString())
        Spacer(modifier = Modifier.height(10.dp))
        DatePicker(
            modifier = Modifier
                .background(color = Color.Green)
                .size(width = 340.dp, height = 300.dp),
            onValueChanged = { selectedDate ->
                selectedValue = selectedDate
            },
            selectedTextStyle = PickerTextStyle(
                fontWeight = FontWeight.Bold,
                textSize = 20.sp,
                textColor = Color.Red
            )
        )
    }
}




