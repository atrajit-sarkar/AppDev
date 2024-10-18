package com.example.introtocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.introtocompose.ui.theme.IntroToComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Main()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview
@Composable
fun Main() {
    IntroToComposeTheme {
        Scaffold(
            Modifier
                .fillMaxWidth()
                .padding(all = 29.dp), containerColor = MaterialTheme.colorScheme.primary
        ) { innerPadding ->
            Greeting(
                name = "Gongo Bongo",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

//@Preview
//@Composable
//fun ShowAge(age: Int = 12) {
//    Text(text = age.toString())
//}
//
//@Preview(name = "Me", showBackground = true)
//@Composable
//fun GreetingPreview() {
//    IntroToComposeTheme {
//        Column {
//
//            Greeting("Android")
//            ShowAge(age = 34)
//        }
//    }
//}