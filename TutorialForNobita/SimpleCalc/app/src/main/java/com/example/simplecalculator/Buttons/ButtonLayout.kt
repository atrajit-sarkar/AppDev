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
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp


@Composable
fun Buttons() {
    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(text = "AC", onClick = { /*TODO*/ }, backgroundColor = Color(0xFF5b5857))
            Button(text = "%", onClick = { /*TODO*/ }, backgroundColor = Color(0xFF5b5857))
            Button(
                onClick = { /*TODO*/ }, backgroundColor = Color(0xFF5b5857),
            ) {
                EraseSymbolIcon()

            }


            Button(text = "÷", onClick = { /*TODO*/ }, backgroundColor = Color(0xFF5b5857))


        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(text = "7", onClick = { /*TODO*/ }, backgroundColor = Color(0xFF5b5857))
            Button(text = "8", onClick = { /*TODO*/ }, backgroundColor = Color(0xFF5b5857))
            Button(text = "9", onClick = { /*TODO*/ }, backgroundColor = Color(0xFF5b5857))
            Button(text = "×", onClick = { /*TODO*/ }, backgroundColor = Color(0xFF5b5857))


        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(text = "4", onClick = { /*TODO*/ }, backgroundColor = Color(0xFF5b5857))
            Button(text = "5", onClick = { /*TODO*/ }, backgroundColor = Color(0xFF5b5857))
            Button(text = "6", onClick = { /*TODO*/ }, backgroundColor = Color(0xFF5b5857))
            Button(text = "-", onClick = { /*TODO*/ }, backgroundColor = Color(0xFF5b5857))


        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(text = "1", onClick = { /*TODO*/ }, backgroundColor = Color(0xFF5b5857))
            Button(text = "2", onClick = { /*TODO*/ }, backgroundColor = Color(0xFF5b5857))
            Button(text = "3", onClick = { /*TODO*/ }, backgroundColor = Color(0xFF5b5857))
            Button(text = "+", onClick = { /*TODO*/ }, backgroundColor = Color(0xFF5b5857))


        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(text = "00", onClick = { /*TODO*/ }, backgroundColor = Color(0xFF5b5857))
            Button(text = "0", onClick = { /*TODO*/ }, backgroundColor = Color(0xFF5b5857))
            Button(text = ".", onClick = { /*TODO*/ }, backgroundColor = Color(0xFF5b5857))
            Button(text = "=", onClick = { /*TODO*/ }, backgroundColor = Color(0xFFfe795d))


        }

    }
}