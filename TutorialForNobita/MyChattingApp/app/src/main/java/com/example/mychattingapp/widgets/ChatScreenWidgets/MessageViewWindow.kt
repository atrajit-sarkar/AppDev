package com.example.mychattingapp.widgets.ChatScreenWidgets

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.Message
import com.example.mychattingapp.LocaldbLogics.ViewModel.ChatAppViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import kotlinx.coroutines.launch

val sampleMessageList = listOf(

    Message(
        id = 1,
        chatId = 1,
        sender = "GB Founder",
        text = "Fuck off",
        timestamp = "69:69",
        reaction = ""
    ),
    Message(
        id = 1,
        chatId = 1,
        sender = "GB Founder",
        text = "Fuck off",
        timestamp = "69:69",
        reaction = ""
    ),
    Message(
        id = 1,
        chatId = 1,
        sender = "GB Founder",
        text = "Fuck off",
        timestamp = "69:69",
        reaction = ""
    ),
    Message(
        id = 1,
        chatId = 1,
        sender = "GB Founder",
        text = "Fuck off skbbcbcskjbcjkfgfhkabcjckabvajk c\n" +
                "dakbaskckacb",
        timestamp = "69:69", reaction = ""
    ),
)
val SampleReactionList = listOf(
    "👍",
    "❤️",
    "😂",
    "😮",
    "😢",
    "🙏",
    "\uD83C\uDF81",
    "\uD83D\uDC3D",
    "\uD83E\uDD96",
    "\uD83D\uDC3C",
    "\uD83E\uDD84",
    "❤\u200D\uD83D\uDD25"

)


@Composable
fun MessageViewWindow(
    innerPadding: PaddingValues = PaddingValues(10.dp),
    messageList: List<Message> = sampleMessageList,
    viewModel: ChatAppViewModel
) {

    // Initialize LazyListState
    val listState by viewModel.lazyListState.collectAsState()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    val coroutineScope = rememberCoroutineScope()

    // Scroll to the last item when the items list changes
    LaunchedEffect(messageList.size, keyboardHeight) {
        if (messageList.isNotEmpty()) {
            coroutineScope.launch {

                listState.scrollToItem(messageList.size - 1)
            }
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .imePadding(), // Adjust padding when keyboard is visible
        contentPadding = PaddingValues(vertical = 8.dp),
        horizontalAlignment = Alignment.End
    ) {
        items(messageList) { message ->
            MessageItem(
                message = message,
                viewModel = viewModel,
                onMessageClick = { viewModel.selectAMessage(message) }
            )

            Spacer(modifier = Modifier.height(10.dp))


        }
    }


}


@Composable
fun MessageItem(
    message: Message,
    isOwnMessage: Boolean = true,// Indicate if the message is sent by the user
    viewModel: ChatAppViewModel,
    onMessageClick: (Message) -> Unit
) {
    val reactionBarVisibility = remember {
        mutableStateOf(false)
    }

    val selectedReaction = remember { mutableStateOf(false) } // Track current reaction


    val animateReaction = remember { mutableStateOf(false) } // Track animation trigger


    Box(
        modifier = Modifier
            .widthIn(max = 350.dp)
    ) {

        // Code for Reaction Bar............
        if (reactionBarVisibility.value) {
            ReactionOverlay(
                onDismiss = { reactionBarVisibility.value = false },
                isVisible = reactionBarVisibility.value,
                onReactionSelect = { reaction ->
                    if (message.reaction == reaction) {
                        // Toggle the selected reaction if the same reaction is selected
                        message.reaction = "" // Clear the reaction when deselected
                        animateReaction.value = true // Trigger animation for new reaction
                        selectedReaction.value = false

                    } else {
                        // Set the new reaction when a different one is selected
                        message.reaction = reaction
                        animateReaction.value = false
                        selectedReaction.value = true

                    }

                    reactionBarVisibility.value = false
                    viewModel.updateMessage(message) //Save the reaction to DB
                    viewModel.clearSelectedMessages()
                }

            )
        }

        if (viewModel.isMessageSelected(message)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min) // Adjust height as needed
                    .clickable {
                        viewModel.deselectMessage(message)
                    }
            ) {
                // Green Card, full width and overlays MessageItem
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(1f), // Ensures it overlays MessageItem
                    colors = CardDefaults.cardColors(Color.Green.copy(alpha = 0.3f)),
                    shape = RectangleShape
                ) {}

                // MessageItem aligned to the end horizontally
                Box(
                    modifier = Modifier.align(Alignment.CenterEnd) // Ensures it's at the end
                ) {
                    SubMessageItem(
                        message,
                        isOwnMessage,
                        reactionBarVisibility,
                        onMessageClick,
                        selectedReaction,
                        viewModel = viewModel
                    )

                }
            }

        } else {
            SubMessageItem(
                message,
                isOwnMessage,
                reactionBarVisibility,
                onMessageClick,
                selectedReaction,
                viewModel = viewModel
            )
        }


    }

}


@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun SubMessageItem(
    message: Message,
    isOwnMessage: Boolean,
    reactionBarVisibility: MutableState<Boolean>,
    onMessageClick: (Message) -> Unit,
    selectedReaction: MutableState<Boolean>,
    viewModel: ChatAppViewModel
) {
    val messageSelectInitiated by viewModel.messageSelectInitiated.collectAsState()
    Log.d("isMessageSelect", "SubMessageItem: $messageSelectInitiated")

    Column(horizontalAlignment = Alignment.End) {


        Text(message.sender)


        // Message Card
        Card(
            shape = RoundedCornerShape(
                topStart = 12.dp,
                topEnd = 12.dp,
                bottomEnd = if (isOwnMessage) 0.dp else 12.dp,
                bottomStart = if (isOwnMessage) 12.dp else 0.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = if (isOwnMessage) Color(0xFF128C7E) else MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier
                .combinedClickable(
                    onClick = {
                        if (messageSelectInitiated) {

                            viewModel.selectAMessage(message)
                        }
                    },
                    onDoubleClick = {
                        reactionBarVisibility.value = true
                        viewModel.deselectMessage(message)

                    },
                    onLongClick = {
                        reactionBarVisibility.value = true
                        viewModel.isMessageSelectInitiated(true)
                        onMessageClick(message)
                    }
                )
        ) {
            Row(modifier = Modifier.padding(5.dp)) {
                Text(
                    message.text,
                    modifier = Modifier
                        .widthIn(max = 250.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    message.timestamp,
                    modifier = Modifier.align(Alignment.Bottom),
                    style = MaterialTheme.typography.labelSmall
                )

                if (message.icons=="Edited"){
                    Spacer(modifier = Modifier.width(5.dp))
                    FaIcon(
                        faIcon = FaIcons.PencilAlt,
                        tint = MaterialTheme.colorScheme.onBackground,
                        size = 15.dp, modifier = Modifier.align(Alignment.Bottom)
                    )
                }
            }
        }

        // Reaction Card (Without Animation) - Always visible when there's a reaction
        if (message.reaction.isNotEmpty() && !selectedReaction.value) {
            // This part always shows the reaction card if there's a reaction
            Card(
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .width(36.dp)
                    .offset(y = (-4).dp)
            ) {
                Text(
                    message.reaction,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }


        AnimatedVisibility(
            visible = selectedReaction.value, // Animate only on new reaction
            enter = fadeIn(animationSpec = tween(300)) + scaleIn(
                initialScale = 0.8f, // Start smaller for popup effect
                animationSpec = tween(300)
            ),
            exit = fadeOut(animationSpec = tween(200)) + scaleOut(
                targetScale = 0.8f,  // Shrinks out for exit effect
                animationSpec = tween(200)
            )
        ) {
            Card(
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .width(36.dp)
                    .offset(y = (-4).dp)
                    .width(40.dp)
            ) {

                Text(
                    message.reaction,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}