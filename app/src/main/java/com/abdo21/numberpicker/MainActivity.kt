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
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abdo21.datepicker.DatePicker
import com.abdo21.numberpicker.ui.theme.NumberPickerTheme
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
                        val values = 1..10

                        NumberPicker(
                            modifier = Modifier
                                .size(width = 100.dp, height = 150.dp),
                            values = values,
                            onValueChanged = {

                            }
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        TimePicker(
                            modifier = Modifier
                                .background(color = Color.LightGray)
                                .size(width = 340.dp, height = 300.dp),
                            onValueChanged = { _, _, _ ->

                            }
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        DatePicker(
                            modifier = Modifier
                                .background(color = Color.LightGray)
                                .size(width = 340.dp, height = 300.dp),
                            onValueChanged = { _, _, _ ->

                            }
                        )
                    }
                }
            }
        }
    }
}


