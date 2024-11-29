package com.example.mycalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mycalculator.Data.HomeScreenData.viewModel.ContactViewModel
import com.example.mycalculator.Navigation.Navigation
import com.example.mycalculator.Screens.HomeScreen
import com.example.mycalculator.ui.theme.MyCalculatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: ContactViewModel by viewModels()
            MyCalculatorTheme {

                Navigation(viewModel)

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyCalculatorTheme {

    }
}