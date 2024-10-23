package com.example.simplecalculator.Buttons

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.simplecalculator.MathsLogic.mathsOperation


@Composable
fun buttons(text: String): MutableState<String> {
    val input = remember {
        mutableStateOf(text)
    }
    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(text = "AC", onClick = { input.value = "" }, backgroundColor = Color(0xFF5b5857))
            Button(
                text = "%",
                onClick = { input.value += "%" },
                backgroundColor = Color(0xFF5b5857)
            )
            Button(
                onClick = { input.value = input.value.dropLast(1) },
                backgroundColor = Color(0xFF5b5857),
            ) {
                EraseSymbolIcon()

            }


            Button(
                text = "÷",
                onClick = { input.value += "÷" },
                backgroundColor = Color(0xFF5b5857)
            )


        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                text = "7",
                onClick = { input.value += "7" },
                backgroundColor = Color(0xFF5b5857)
            )
            Button(
                text = "8",
                onClick = { input.value += "8" },
                backgroundColor = Color(0xFF5b5857)
            )
            Button(
                text = "9",
                onClick = { input.value += "9" },
                backgroundColor = Color(0xFF5b5857)
            )
            Button(
                text = "×",
                onClick = { input.value += "×" },
                backgroundColor = Color(0xFF5b5857)
            )


        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                text = "4",
                onClick = { input.value += "4" },
                backgroundColor = Color(0xFF5b5857)
            )
            Button(
                text = "5",
                onClick = { input.value += "5" },
                backgroundColor = Color(0xFF5b5857)
            )
            Button(
                text = "6",
                onClick = { input.value += "6" },
                backgroundColor = Color(0xFF5b5857)
            )
            Button(
                text = "-",
                onClick = { input.value += "-" },
                backgroundColor = Color(0xFF5b5857)
            )


        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                text = "1",
                onClick = { input.value += "1" },
                backgroundColor = Color(0xFF5b5857)
            )
            Button(
                text = "2",
                onClick = { input.value += "2" },
                backgroundColor = Color(0xFF5b5857)
            )
            Button(
                text = "3",
                onClick = { input.value += "3" },
                backgroundColor = Color(0xFF5b5857)
            )
            Button(
                text = "+",
                onClick = { input.value += "+" },
                backgroundColor = Color(0xFF5b5857)
            )


        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                text = "00",
                onClick = { input.value += "00" },
                backgroundColor = Color(0xFF5b5857)
            )
            Button(
                text = "0",
                onClick = { input.value += "0" },
                backgroundColor = Color(0xFF5b5857)
            )
            Button(
                text = ".",
                onClick = { input.value += "." },
                backgroundColor = Color(0xFF5b5857)
            )
            Button(text = "=", onClick = {
                input.value=mathsOperation(input.value)
            }, backgroundColor = Color(0xFFfe795d))


        }

    }
    return input
}