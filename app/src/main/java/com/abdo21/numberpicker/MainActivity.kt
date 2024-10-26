package com.abdo21.numberpicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                        ) {
                            NumberPickerExamplePreview()
                            StringPickerExamplePreview()
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider()
                        AMPMTimePickerExamplePreview()
                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider()
                        DatePickerExamplePreview()
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

    var selectedNumber by remember { mutableIntStateOf(0) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = "$selectedNumber")
        Spacer(modifier = Modifier.height(10.dp))
        NumberPicker(
            modifier = Modifier
                .size(width = 70.dp, height = 120.dp)
                .background(color = Color.Cyan),
            values = values,
            onValueChanged = { selectedIndex ->
                selectedNumber = values.elementAt(selectedIndex)
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

    var selectedString by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = selectedString)
        Spacer(modifier = Modifier.height(10.dp))
        StringPicker(
            modifier = Modifier
                .size(width = 70.dp, height = 120.dp)
                .background(color = Color.Blue),
            values = values,
            onValueChanged = { index ->
                selectedString = values[index]
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
    var selectedAMPMTime by remember { mutableStateOf(AMPMTime.Zero) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = "${selectedAMPMTime.hour}:${selectedAMPMTime.minute} ${selectedAMPMTime.timeMode}")
        Spacer(modifier = Modifier.height(10.dp))

        TimePicker(
            initialTime = AMPMTime.NOW,
            modifier = Modifier
                .background(color = Color.Green)
                .size(width = 250.dp, height = 200.dp),
            onValueChanged = { time ->
                selectedAMPMTime = time
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
    var selectedH24Time by remember { mutableStateOf(H24Time.Zero) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = selectedH24Time.toString())
        Spacer(modifier = Modifier.height(10.dp))

        TimePicker(
            initialTime = H24Time.NOW,
            modifier = Modifier
                .background(color = Color.Green)
                .size(width = 250.dp, height = 200.dp),
            onValueChanged = { time ->
                selectedH24Time = time
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
    var selectedDate by remember { mutableStateOf(Date.Default) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = "${selectedDate.day} ${Date.shortMonthNames[selectedDate.month-1]} ${selectedDate.year}")
        Spacer(modifier = Modifier.height(10.dp))
        DatePicker(
            modifier = Modifier
                .background(color = Color.Magenta)
                .size(width = 250.dp, height = 200.dp),
            onValueChanged = { date ->
                selectedDate = date
            },
            selectedTextStyle = PickerTextStyle(
                fontWeight = FontWeight.Bold,
                textSize = 20.sp,
                textColor = Color.Yellow
            ),
            unselectedTextStyle = PickerTextStyle(
                textColor = Color.White
            )
        )
    }
}
