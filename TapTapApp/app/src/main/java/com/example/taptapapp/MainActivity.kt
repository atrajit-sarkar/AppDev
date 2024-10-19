package com.example.taptapapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taptapapp.ui.theme.TapTapAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TapTapAppTheme {
                Myapp()
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Myapp() {
    // State variables for the app
    var moneyCounter by remember { mutableStateOf(0) }
    var money by remember { mutableStateOf(100) }
    var reset by remember { mutableStateOf(false) }
    //Another way of creating remember
    var reset1 = remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFF546E7A)
    ) {
        // Box composable is defined to align contents.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Gongo Bongo চরম বেকারত্ব",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    textAlign = TextAlign.Center
                )


                Spacer(modifier = Modifier.height(69.dp))

                // Display current money
                Text(
                    text = "$$money",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )

                Spacer(modifier = Modifier.height(50.dp))

                // Circle tap button
                CreateCircle(
                    moneyCounter = moneyCounter,
                    onTapClick = {
                        moneyCounter += 1
                        money += 10
                        reset1.value = false
                    }
                )

                Spacer(modifier = Modifier.height(50.dp))

                // Reset button
                CreateRectangle(
                    onResetClick = {
                        reset1.value = true
                        moneyCounter = 0
                        money = 100
                    }
                )
                Spacer(modifier = Modifier.height(69.dp))
                Text(
                    text = "© টিপে টিপে বাড়াও! তারপর screenshot পাঠাও!",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    ),
                )

            }
        }
    }
}

@Composable
fun CreateRectangle(onResetClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(3.dp)
            .height(50.dp)
            .width(150.dp)
            .clickable { onResetClick() },
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Reset Counter")
        }
    }
}

@Composable
fun CreateCircle(moneyCounter: Int, onTapClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(3.dp)
            .size(100.dp)
            .clickable { onTapClick() },
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Tap $moneyCounter")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TapTapAppTheme {
        Myapp()
    }
}
