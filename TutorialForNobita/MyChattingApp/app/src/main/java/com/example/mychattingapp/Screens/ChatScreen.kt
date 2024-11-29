package com.example.mychattingapp.Screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import com.example.mychattingapp.ui.theme.MyChattingAppTheme
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mychattingapp.LocaldbLogics.ViewModel.ChatAppViewModel
import com.example.mychattingapp.widgets.ChatScreenWidgets.ChatInputField
import com.example.mychattingapp.widgets.ChatScreenWidgets.ChatScreenTopBar
import com.example.mychattingapp.widgets.ChatScreenWidgets.MessageSelectTopBar
import com.example.mychattingapp.widgets.ChatScreenWidgets.MessageViewWindow
import com.example.mychattingapp.widgets.ChatScreenWidgets.sampleMessageList
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    viewModel: ChatAppViewModel,
    lastSeen: String = "12:00 PM",
    navController: NavController = rememberNavController(),
    chatId: Int,
    messageId:Int=-1
) {


    val textFiledValue = remember { mutableStateOf("") }

    val messageList by viewModel.getMessageById(chatId).observeAsState(emptyList())
    val currentUser by viewModel.getUserById(chatId).observeAsState(emptyList())


    val sendIcon = remember { mutableStateOf(false) }
    val MicIcon = remember { mutableStateOf(false) }

    // Message delete implementation...........
    val selectedMessages by viewModel.selectedMessages.collectAsState()
    Log.d("SelectedMessages", "ChatScreen: $selectedMessages")

    if (selectedMessages.isEmpty()) {
        viewModel.isMessageSelectInitiated(false)
    }

    val listState by viewModel.lazyListState.collectAsState()
    val coroutineScope = rememberCoroutineScope()


    val showScrollToBottomFab by remember {
        derivedStateOf {
            val lastIndex = messageList.lastIndex
            val visibleItemsInfo = listState.layoutInfo.visibleItemsInfo
            // Check if the last visible item is not the last message in the list
            if (visibleItemsInfo.isNotEmpty()) {
                val lastVisibleItemIndex = visibleItemsInfo.last().index
                lastVisibleItemIndex < lastIndex
            } else {
                false
            }
        }
    }




    MyChattingAppTheme {
        Scaffold(
            floatingActionButton = {
                AnimatedVisibility(visible = showScrollToBottomFab) {

                    FloatingActionButton(
                        containerColor = MaterialTheme.colorScheme.background,
                        shape = CircleShape,
                        modifier = Modifier.size(35.dp),
                        onClick = {
                            coroutineScope.launch {
                                listState.animateScrollToItem(messageList.lastIndex)
                            }
                        }
                    ) {
                        FaIcon(
                            faIcon = FaIcons.AngleDoubleDown,
                            tint = MaterialTheme.colorScheme.onSurface,
                            size = 20.dp,
                        )
                    }
                }
            },

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
                AnimatedVisibility(visible = selectedMessages.isNotEmpty()) {
                    MessageSelectTopBar(
                        selectedChats = "${selectedMessages.size}",
                        viewModel = viewModel,
                        messageList = selectedMessages,
                        onDismiss = { viewModel.clearSelectedMessages() }
                    )
                }

            },
            bottomBar = {
                ChatInputField(textFiledValue, sendIcon, MicIcon, viewModel, chatId = chatId)
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            MessageViewWindow(
                innerPadding = innerPadding,
                messageList = messageList,
                viewModel = viewModel,
                messageId = messageId
            )

        }
    }
}





