package com.example.mychattingapp.Screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.mychattingapp.NavHost.navigateIfNotFast
import com.example.mychattingapp.ui.theme.MyChattingAppTheme
import com.example.mychattingapp.widgets.HomeScreenWidgets.TopBarFun
import com.guru.fontawesomecomposelib.FaIcons

@Composable
fun Contacts() {
    MyChattingAppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBarFun(firstIcon = {
                    IconButton(onClick = {
                        navigateIfNotFast {
                        }

                    }) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack, contentDescription = null


                        )
                    }
                },
                    text = "Settings",
                    color = Color(0xFF2d944c),
                    body = {
                        IconButton(
                            onClick = {}

                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                )


            },


//            bottomBar = {},
        ) { innerPadding ->


            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                item {
                    SettingsRow(
                        label = "New group",
                        iconSolid = FaIcons.UserFriends,


                        )
                }
            }


        }
    }
}

@Preview
@Composable
fun PreviewContacts() {
    Contacts()
}
