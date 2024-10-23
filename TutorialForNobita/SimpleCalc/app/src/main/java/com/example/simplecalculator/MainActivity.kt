package com.example.simplecalculator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
//import com.example.simplecalculator.Buttons.Buttons
import com.example.simplecalculator.Buttons.buttons
import com.example.simplecalculator.Inputfield.InputField
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleCalculator()

        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SimpleCalculator() {
    val input = remember {
        mutableStateOf("")
    }
    SimpleCalculatorTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .padding(top = 50.dp)
                    .fillMaxSize(),

                ) {
                InputField(input.value)
                Spacer(modifier = Modifier.height(10.dp))
                input.value = buttons(input.value).value

            }

        }
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimpleCalculator()
}