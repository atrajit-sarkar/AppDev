package com.example.mychattingapp.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mychattingapp.ui.theme.MyChattingAppTheme
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.Message
import com.example.mychattingapp.LocaldbLogics.ViewModel.ChatAppViewModel
import com.example.mychattingapp.widgets.ChatScreenWidgets.ChatInputField
import com.example.mychattingapp.widgets.ChatScreenWidgets.ChatScreenTopBar


@Composable
fun ChatScreen(
    viewModel: ChatAppViewModel,
    lastSeen: String = "12:00 PM",
    navController: NavController = rememberNavController(),
    chatId: Int
) {
    val textFiledValue = remember {
        mutableStateOf("")
    }

    val messageList by viewModel.allChats.observeAsState(emptyList())
    val userList by viewModel.allUsers.observeAsState(emptyList())


    val sendIcon = remember {
        mutableStateOf(false)
    }
    val MicIcon = remember {
        mutableStateOf(false)
    }
    var contactName:String=""

    for (user in userList){
        if (user.id==chatId){
            contactName=user.userName

        }
    }


    MyChattingAppTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.ime),
            topBar = {
                ChatScreenTopBar(navController, contactName, lastSeen)

            },
            bottomBar = {
                ChatInputField(textFiledValue, sendIcon, MicIcon, viewModel, chatId = chatId)
            }
        ) { innerPadding ->
            // Message list using LazyColumn
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .imePadding(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(messageList) { message ->
                        if (message.chatId == chatId) {

                            MessageItem(message, viewModel)
                        }
                    }
                }
            }
        }
    }
}


// Showing Message Layout Design......
@Composable
fun MessageItem(
    message: Message,
    viewModel: ChatAppViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val isPressed = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                showDialog.value = true
            }
    ) {
        // Dialog for Pop-Up Window
        if (showDialog.value) {
            Dialog(onDismissRequest = { showDialog.value = false }) {
                Surface(
                    modifier = Modifier.padding(16.dp),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.background,
                    tonalElevation = 8.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Want to delete?")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = {
                            viewModel.deleteMessage(message)
                            showDialog.value = false
                        }) {
                            Text("Yes")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { showDialog.value = false }) {
                            Text("Close")
                        }
                    }
                }
            }
        }


        Text(
            text = message.sender,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = message.text,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = message.timestamp,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 4.dp)
        )
    }
}

//@Preview
//@Composable
//fun PreviewChatScreen() {
//    ChatScreen()
//}

