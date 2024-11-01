package com.example.daycalculator.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.daycalculator.Logic.dayCalculation
import com.example.daycalculator.widgets.DayCalcButton
import com.example.daycalculator.widgets.DayCalcInputField
import com.example.daycalculator.widgets.NavIcon
import com.example.daycalculator.widgets.ScreenLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayCalculatorScreen() {
    val inputDOB = remember { mutableStateOf("") }
    val placeHolder = remember { mutableStateOf("DD/MM/YYYY") }
    val placeHolderColor = remember { mutableLongStateOf(0xFFd6d2cf) }
    val borderColor = remember { mutableLongStateOf(0xFF74c2f7) }
    val showResult = remember { mutableStateOf(false) }
    val resultWeekDay = remember { mutableStateOf("hello") }
//    val isError = remember { mutableStateOf(false) }

    ScreenLayout { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DayCalcInputField(
                placeholder = {
                    Text(
                        text = placeHolder.value,
                        color = Color(placeHolderColor.longValue)
                    )
                },
                label = "Enter your DOB below",
                text = inputDOB.value,
                onTextChange = {
                    inputDOB.value = it

                },
                borderColor = borderColor.longValue
            )

            if (inputDOB.value != "") {
                borderColor.longValue = 0xFF74c2f7
                placeHolder.value = "DD/MM/YYYY"
                placeHolderColor.longValue = 0xFFd6d2cf
            } else {
                showResult.value = false
            }

            Spacer(modifier = Modifier.height(8.dp))

            DayCalcButton(text = "Find",
                onClick = {
                    if (!validateDate(inputDOB.value)) {
                        inputDOB.value = ""
                        placeHolder.value = "Invalid Format"
                        placeHolderColor.longValue = 0xFFf31c15
                        borderColor.longValue = 0xFFf31c15


                    } else {
                        showResult.value = true
                        resultWeekDay.value = dayCalculation(inputDOB.value)
                    }
                })

            if (showResult.value) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(69.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(Color.DarkGray),
                    elevation = CardDefaults.cardElevation(12.dp),
                    border = BorderStroke(width = 2.dp, color = Color.White)
                ) {
                    Text(
                        text = resultWeekDay.value,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 15.dp),
                        fontSize = 30.sp
                    )

                }
            }

        }
    }
}


private fun validateDate(date: String): Boolean {
    return try {
        val parts = date.split("/")
        val dd = parts[0].toIntOrNull() ?: return false
        val mm = parts[1].toIntOrNull() ?: return false
        val yyyy = parts[2].toIntOrNull() ?: return false
        dd in 1..31 && mm in 1..12 && yyyy >= 1600
    } catch (e: Exception) {
        false
    }
}
