package com.example.mychattingapp.Screens

import android.annotation.SuppressLint
import android.util.Log
import android.util.Size
import androidx.collection.mutableFloatListOf
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mychattingapp.NavHost.navigateIfNotFast
import com.example.mychattingapp.ui.theme.MyChattingAppTheme
import com.example.mychattingapp.widgets.HomeScreenWidgets.TopBarFun
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIconType
import com.guru.fontawesomecomposelib.FaIcons


@Composable
fun SettingsScreen(
    navController: NavController
) {
    val fontSize = remember {
        mutableFloatStateOf(0.8f)
    }
    MyChattingAppTheme {


        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBarFun(firstIcon = {
                    IconButton(onClick = {
                        navigateIfNotFast {
                            navController.popBackStack()
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


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
//                            .padding(innerPadding)
                            .padding(all = 10.dp)
                            .clickable { },

//                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,

                        ) {


                        Card(
                            shape = RoundedCornerShape(90.dp),
                            modifier = Modifier.size(80.dp),
                            onClick = {}
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                FaIcon(
                                    faIcon = FaIcons.UserCircle,
                                    size = 80.dp

                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(Color.Transparent)
                        ) {
                            Row {
                                Column(Modifier.width(200.dp)) {
                                    Text(
                                        text = "Atrajit sarkar",
                                        color = Color.Green,
                                        fontSize = (10 + fontSize.floatValue * 25).toInt().sp,
                                        textAlign = TextAlign.Start,

                                        )
                                    Text(
                                        text = "Hey There I Am Using whatsapp",
                                        color = Color.Green,
                                        fontSize = (10 + fontSize.floatValue * 5).toInt().sp,
                                        textAlign = TextAlign.Left,

                                        )
                                }

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
                    HorizontalDivider(thickness = 0.dp, color = Color.White)
                    Column {

                        SettingsRow(
                            label = "Account",
                            caption = "security notifications,change number",
                            iconSolid = FaIcons.Key,
                            fontSize = (10 + fontSize.floatValue * 25).toInt().sp
                        )
                        SettingsRow(
                            label = "Privacy",
                            caption = "Block contacts,disappering massages",
                            iconSolid = FaIcons.Lock,
                            fontSize = (10 + fontSize.floatValue * 25).toInt().sp
                        )
                        SettingsRow(
                            label = "Avatar",
                            caption = "Creat,edit,profilr photo",
                            iconSolid = FaIcons.Male,
                            fontSize = (10 + fontSize.floatValue * 25).toInt().sp
                        )
                        SettingsRow(
                            label = "Lists",
                            caption = "Manage people and groups",
                            iconSolid = FaIcons.List,
                            fontSize = (10 + fontSize.floatValue * 25).toInt().sp
                        )
                        SettingsRow(
                            label = "Chats",
                            caption = "Theme,walpapers,chat history",
                            iconBrand = FaIcons.Rocketchat,
                            fontSize = (10 + fontSize.floatValue * 25).toInt().sp
                        )
                        SettingsRow(
                            label = "Notifications",
                            caption = "massage,group & call tones",
                            iconSolid = FaIcons.Bell,
                            fontSize = (10 + fontSize.floatValue * 25).toInt().sp
                        )
                    }
//                    Slider(
//                        value = fontSize.floatValue,
//                        onValueChange = {
//                            fontSize.floatValue = it
//                        },
//                        modifier = Modifier.padding(horizontal = 10.dp),
//                        steps = 5
//                    )

                }


            }


        }
    }


}


@Composable
fun SettingsRow(
    label: String,
    caption: String = "",
    size: TextUnit = 15.sp,
    iconSolid: FaIconType.SolidIcon? = null,
    iconBrand: FaIconType.BrandIcon? = null,
    iconRegular: FaIconType.RegularIcon? = null,
    cardOnClick: () -> Unit = {},
    iconOnClick: () -> Unit = {},
    fontSize: TextUnit = 33.sp,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 10.dp),

        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            colors = CardDefaults.cardColors(Color.Transparent),
            onClick = {
                cardOnClick()
            }
        ) {
            Row {
                IconButton(
                    onClick = {
                        iconOnClick()
                    }

                ) {
                    if (iconSolid != null) {

                        FaIcon(
                            faIcon = iconSolid,
                            tint = Color.White
                        )
                    } else if (iconBrand != null) {
                        FaIcon(
                            faIcon = iconBrand,
                            tint = Color.White
                        )
                    } else if (iconRegular != null) {
                        FaIcon(
                            faIcon = iconRegular,
                            tint = Color.White
                        )
                    }
                }


                Column {
                    Text(
                        text = label,
                        color = Color.Green,
                        fontSize = fontSize,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = caption,
                        color = Color.Green,
                        fontSize = size,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }


            }


        }
    }
}


@Preview
@Composable
fun PreviewSetting() {
    SettingsScreen(navController = rememberNavController())
}