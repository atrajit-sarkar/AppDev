package com.example.mychattingapp.Screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.Message
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.User
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
    val textFiledValue = remember { mutableStateOf("") }

    val messageList by viewModel.getMessageById(chatId).observeAsState(emptyList())
    val currentUser by viewModel.getUserById(chatId).observeAsState(emptyList())

    val sendIcon = remember { mutableStateOf(false) }
    val MicIcon = remember { mutableStateOf(false) }


    MyChattingAppTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.ime)
                .background(MaterialTheme.colorScheme.background), // Ensure dark background
            topBar = {

                if (currentUser.isNotEmpty()) {

                    ChatScreenTopBar(
                        navController,
                        currentUser[0].userName,
                        lastSeen,
                        viewModel,
                        user = currentUser[0]
                    )
                }

            },
            bottomBar = {
                ChatInputField(textFiledValue, sendIcon, MicIcon, viewModel, chatId = chatId)
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background) // Ensure dark background
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .imePadding(), // Adjust padding when keyboard is visible
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(messageList) { message ->
                        MessageItem(
                            message = message,
                            viewModel = viewModel,
                            isOwnMessage = true // Dynamically set
                        )


                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageItem(
    message: Message,
    viewModel: ChatAppViewModel,
    isOwnMessage: Boolean // Indicate if the message is sent by the user
) {
    val showDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .combinedClickable(
                onLongClick = { showDialog.value = true },
                onClick = { /* Add optional click behavior */ }
            )
    ) {
        // Dialog for deleting the message
        ShowDeleteMessageDialogue(showDialog, viewModel, message)
        Text(
            text = message.sender,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Box(
            modifier = Modifier
                .background(
                    color = if (isOwnMessage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
                .wrapContentSize()
                .widthIn(min = 64.dp, max = 200.dp)
        ) {

            Column {
                Text(
                    text = message.text,
                    fontSize = 16.sp,
                    color = if (isOwnMessage) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = message.timestamp,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}


@Composable
private fun ShowDeleteMessageDialogue(
    showDialog: MutableState<Boolean>,
    viewModel: ChatAppViewModel,
    message: Message
) {
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
}


