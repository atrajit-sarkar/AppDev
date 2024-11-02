package com.example.mychattingapp.Screens

//package com.example.mychattingapp.Screens

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.ColumnScopeInstance.weight
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.weight
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mychattingapp.ChatsData.Message
import com.example.mychattingapp.ui.theme.MyChattingAppTheme
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.filled.Send
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Test2Screen(contactName: String, lastSeen: String) {
    val textFiledValue = remember {
        mutableStateOf("")
    }

    val messageList = remember {
        mutableStateListOf<Message>()
    }


    MyChattingAppTheme {
        Column {

            TopAppBar(
                navigationIcon = {
                    // Back button
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                title = {

                },
                actions = {
                    Row(
                        modifier = Modifier.padding(start = 35.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.padding(start = 0.dp), // No padding from the start
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Contact Profile Picture
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Profile Picture",
                                modifier = Modifier.size(40.dp),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                            // Contact Name and Last Seen
                            Column(
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = contactName,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    maxLines = 1
                                )
                                Text(
                                    text = lastSeen,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }

                        // Action Icons (Notifications, Call, More Options)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = {}) {
                                FaIcon(
                                    faIcon = FaIcons.Video,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                            IconButton(onClick = {}) {
                                Icon(
                                    imageVector = Icons.Default.Call,
                                    contentDescription = "Call",
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                            IconButton(onClick = {}) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "More Options",
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize(),
//            topBar = {
//
//            },
//            bottomBar = {
//
//            }
            ) {
                // Message list using LazyColumn
                Column(
                    modifier = Modifier
                        .padding(12.dp)
                        .windowInsetsPadding(WindowInsets.ime)
                        .fillMaxWidth()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                            .imePadding()
                            .windowInsetsPadding(WindowInsets.ime),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(messageList) { message ->
                            MessageItem(message)
                        }
                    }


                    BottomAppBar(
                        containerColor = BottomAppBarDefaults.containerColor
                    ) {
                        BasicTextField(
                            value = textFiledValue.value,
                            onValueChange = {
                                textFiledValue.value = it
                            },
                            cursorBrush = SolidColor(Color.Green),
                            textStyle = TextStyle(
                                color = Color.LightGray,
                                fontSize = 20.sp
                            ),
                            modifier = Modifier
                                .width(300.dp)
                                .padding(8.dp)
                                .background(Color.Black, shape = MaterialTheme.shapes.medium)
                                .padding(8.dp)
                                .height(30.dp),
                            decorationBox = { innerTextField ->
                                if (textFiledValue.value.isEmpty()) {
                                    Text("Message", color = Color.Gray)
                                }
                                innerTextField()
                            }
                        )

                        IconButton(
                            colors = IconButtonDefaults.iconButtonColors(Color.Green),
                            onClick = {
                                if (textFiledValue.value.isNotBlank()) {
                                    val currentTime =
                                        SimpleDateFormat(
                                            "HH:mm",
                                            Locale.getDefault()
                                        ).format(Date())
                                    messageList.add(
                                        Message(
                                            sender = "Atrajit",
                                            text = textFiledValue.value,
                                            timestamp = currentTime
                                        )
                                    ) // Add message at the top
                                    textFiledValue.value = "" // Clear the input
                                    // Scroll to the newest message
//                                Log.d("message", "ChatScreen: $messageList")

                                }

                            }
                        ) {
                            if (textFiledValue.value != "") {

                                Icon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            } else {
                                FaIcon(
                                    faIcon = FaIcons.Microphone
                                )

                            }

                        }

                    }
                }


            }
        }
    }
}


//@Composable
//fun MessageItem(message: Message) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//    ) {
//        Text(
//            text = message.sender,
//            fontSize = 14.sp,
//            color = MaterialTheme.colorScheme.primary,
//            modifier = Modifier.padding(bottom = 4.dp)
//        )
//        Text(
//            text = message.text,
//            fontSize = 16.sp,
//            color = MaterialTheme.colorScheme.onBackground
//        )
//        Text(
//            text = message.timestamp,
//            fontSize = 12.sp,
//            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
//            modifier = Modifier
//                .align(Alignment.End)
//                .padding(top = 4.dp)
//        )
//    }
//}

@Preview
@Composable
fun PreviewChatScreen2() {
    Test2Screen(
        contactName = "GB Tukun2.0",
        lastSeen = "last seen today at 21:02"
    )
}
