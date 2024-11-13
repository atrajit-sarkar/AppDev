package com.example.mychattingapp.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.mychattingapp.ChatsData.loadChannels
import com.example.mychattingapp.ChatsData.loadStatuses
import com.example.mychattingapp.NavHost.navigateIfNotFast
import com.example.mychattingapp.widgets.HomeScreenWidgets.TopBarFun
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons


@Composable
fun UpdateScreen() {
    val listOfUserStatuses = loadStatuses()
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarFun(
                firstIcon = {
                },
                text = "Updates",
                color = Color.Transparent,
                body = {
                    IconButton(
                        onClick = {}

                    )
                    {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            tint = Color.Black

                        )
                    }
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.Black,
//                        modifier = Modifier.()
                    )
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )

                }

            )


        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)
            .padding(10.dp)) {
            // Before Channel Contents........
            item {
                // Status Contents.............
                LazyRow(horizontalArrangement = Arrangement.SpaceEvenly) {
                    items(
                        loadStatuses()
                    ) { i ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                i.statusicon()
                                Text(i.username)

                            }
                        }
                    }


                }

                HorizontalDivider(thickness = 2.dp, color = Color.Black)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(4.dp)
                ) {
                    Box {
                        Text("Channels", fontSize = 20.sp)
                    }
                    Box(modifier = Modifier.clickable { }) {
                        Text("Explore", fontSize = 20.sp)
                    }
                }


            }

            // Rendering Channels.........
            items(loadChannels()) { channel ->

                Card(modifier = Modifier.fillMaxSize()) {
                    Row(horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(5.dp)) {
                        channel.channelicon()
                        Text(channel.channelname,
                            modifier = Modifier.padding(horizontal = 10.dp))
                    }
                }
                Spacer(modifier=Modifier.height(10.dp))
            }

        }
    }
}

@Preview
@Composable
fun PreviewStatus() {
    UpdateScreen()
}
