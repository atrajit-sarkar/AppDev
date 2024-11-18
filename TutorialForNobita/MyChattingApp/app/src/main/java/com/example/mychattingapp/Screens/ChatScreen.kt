package com.example.mychattingapp.Screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.Message
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.User
import com.example.mychattingapp.LocaldbLogics.ViewModel.ChatAppViewModel
import com.example.mychattingapp.widgets.ChatScreenWidgets.ChatInputField
import com.example.mychattingapp.widgets.ChatScreenWidgets.ChatScreenTopBar
import com.example.mychattingapp.widgets.ChatScreenWidgets.MessageSelectTopBar
import com.example.mychattingapp.widgets.ChatScreenWidgets.MessageViewWindow
import com.example.mychattingapp.widgets.ChatScreenWidgets.ReactionOverlay

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
    val isMessageSelected = remember {
        mutableStateOf(false)
    }

    var selectedMessageId = remember {
        mutableStateOf(-1)
    }

    val selectedMessage by viewModel.getMessageById(selectedMessageId.value).observeAsState(
        emptyList()
    )


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
                if (isMessageSelected.value) {
                    MessageSelectTopBar(
                        viewModel = viewModel,
                        message = selectedMessage[0],
                        onDismiss = {
                            isMessageSelected.value = false
                        }
                    )
                }

            },
            bottomBar = {
                ChatInputField(textFiledValue, sendIcon, MicIcon, viewModel, chatId = chatId)
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            selectedMessageId = MessageViewWindow(
                innerPadding,
                messageList,
                isMessageSelected = isMessageSelected,
                viewModel = viewModel
            )

        }
    }
}





