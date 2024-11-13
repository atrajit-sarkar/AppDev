package com.example.daycalculator.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.guru.fontawesomecomposelib.FaIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenLayout(body:@Composable (PaddingValues)->Unit){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Day Calculator", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(Color(0xFF6200EE)),
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFF232323),
                tonalElevation = 20.dp,
                modifier = Modifier.height(120.dp)
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item {
                        NavIcon(
                            caption = "Day Calc",
                            icon = Icons.Rounded.Home,
                            onClick = {}
                        )
                        NavIcon(
                            caption = "Prime Calc",
                            faIconSolidIcon = FaIcons.Calculator,
                            onClick = {}
                        )


                    }

                }

            }
        }
    ){innerPadding->
        body(innerPadding)


    }
}