package com.example.mychattingapp.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
//import androidx.benchmark.perfetto.Row
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mychattingapp.ui.theme.MyChattingAppTheme
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Settings() {
    MyChattingAppTheme {


        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBarFun(firstIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, contentDescription = null


                    )
                }, text = "Settings", color = Color(0xFF2d944c), width = 170.dp, body = {
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
                })


            },


            bottomBar = {},
        ) { innerPadding ->
            LazyColumn {
                item {


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(innerPadding)
                            .padding(all = 10.dp)
                            .clickable {  },

                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,

                        ) {


                        Card(
                            shape = RoundedCornerShape(90.dp), modifier = Modifier.size(100.dp), onClick = {}
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
//                                Text(
//                                    text = "pic",
//                                    color = Color.Green,
//                                    fontSize = 30.sp
//                                )
//                                Icon(
//                                    imageVector = Icons.Default.AccountCircle,
//                                    contentDescription = null,
//                                    tint = Color.Gray,
//                                    modifier = Modifier.fillMaxSize())
                                FaIcon(
                                    faIcon = FaIcons.UserCircle,
                                    size= 100.dp

                                )
                            }
                        }
                        Card(modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(Color.Transparent)) {
                            Row {

                                Text(
                                    text = "Atrajit sarkar",
                                    color = Color.Green,
                                    fontSize = 35.sp,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                IconButton(
                                    onClick = {}

                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Share,
                                        contentDescription = null,
                                        tint = Color.Green,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }


                        }


                    }
                    HorizontalDivider(color = MaterialTheme.colorScheme.onBackground)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                            .padding(innerPadding)
                            .padding(all = 10.dp),

                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,

                        ) {}
                }

            }


        }
    }


}


@Preview
@Composable
fun PreviewSetting() {
    Settings()
}