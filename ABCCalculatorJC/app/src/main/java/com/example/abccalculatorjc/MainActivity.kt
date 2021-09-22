package com.example.abccalculatorjc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.abccalculatorjc.ui.theme.ABCCalculatorJCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var a = ""
        var b = ""
        var c = ""
        var d = ""

        setContent {
            ABCCalculatorJCTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        TopAppBar(
                            modifier = Modifier.fillMaxWidth(),
                            backgroundColor = MaterialTheme.colors.primary,
                            contentPadding = AppBarDefaults.ContentPadding
                        ) {
                            Text(
                                text = "Perhitungan Rumus ABC",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(8.dp, 0.dp)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(16.dp)
                            ) {
                                ValueInputField(label = "Nilai A") { a = it }
                                ValueInputField(label = "Nilai B", onTextChanged = {
                                    b = it
                                })
                                ValueInputField(label = "Nilai C", onTextChanged = {
                                    c = it
                                })
                            }
                            Card(
                                elevation = 16.dp,
                                modifier = Modifier
                                    .padding(0.dp)
                                    .background(color = Color.White)
                                    .fillMaxWidth(),
                            ) {
                                Button(
                                    onClick = { },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "HITUNG",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ValueInputField(label: String, onTextChanged: (input: String) -> Unit) {
    val input = remember { mutableStateOf("") }

    OutlinedTextField(
        value = input.value,
        onValueChange = {
            input.value = it
            onTextChanged(it)
        },
        label = { Text(text = label) },
        placeholder = { Text(text = "Masukkan $label") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 4.dp)
    )
}
