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
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.mychattingapp.FireBaseLogics.FireBseSetings.FirestoreHelper
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.Message
import com.example.mychattingapp.LocaldbLogics.ViewModel.ChatAppViewModel
import com.example.mychattingapp.Utils.DateUtils.DateHeader
import com.example.mychattingapp.Utils.DateUtils.formatDateForDisplay
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

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
    viewModel: ChatAppViewModel,
    messageId: Int
) {

    // Initialize LazyListState
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    val coroutineScope = rememberCoroutineScope()

    val lazyListState by viewModel.lazyListState.collectAsState()
    val chatLoading by viewModel.chatLoading.collectAsState()


    // Scroll to the specific message on first load
    if (messageId != -1) {

        LaunchedEffect(messageId) {

            lazyListState.scrollToItem(messageId)

        }
    } else {

        // Scroll to the last item when the items list changes
        LaunchedEffect(messageList.size, keyboardHeight) {
            if (messageList.isNotEmpty()) {
                coroutineScope.launch {

                    lazyListState.scrollToItem(messageList.lastIndex)
                }
            }
        }
    }

    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val groupedMessages = messageList.groupBy { message ->
        try {
            val date = inputFormat.parse(message.timestamp) // Parse the date
            outputFormat.format(date!!) // Format it back to "dd/MM/yyyy"
        } catch (e: Exception) {
            "Invalid Date" // Fallback for parsing errors
        }
    }



    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .imePadding(), // Adjust padding when keyboard is visible
        contentPadding = PaddingValues(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!chatLoading) {
            groupedMessages.forEach { (date, messagesForDate) ->
                if (date != null) {
                    item {
                        DateHeader(date = date)
                    }

                    items(messagesForDate) { message ->
                        MessageItem(
                            message = message,
                            viewModel = viewModel,
                            onMessageClick = { viewModel.selectAMessage(message) },
                        )

                        Spacer(modifier = Modifier.height(10.dp))


                    }
                }


            }
        } else {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(16.dp),

                    )
            }
        }
    }
}


@Composable
fun MessageItem(
    message: Message,
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
                    val updates = mapOf(
                        "reaction" to reaction,           // Update a single field

                    )
                    viewModel.updateMessageItem(message.messageId, updates)
                    Log.d("messageid", "MessageItem: ${message.messageId}")

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
                        reactionBarVisibility,
                        onMessageClick,
                        selectedReaction,
                        viewModel = viewModel,

                        )

                }
            }

        } else {
            SubMessageItem(
                message,
                reactionBarVisibility,
                onMessageClick,
                selectedReaction,
                viewModel = viewModel,

                )
        }


    }

}


@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun SubMessageItem(
    message: Message,
    reactionBarVisibility: MutableState<Boolean>,
    onMessageClick: (Message) -> Unit,
    selectedReaction: MutableState<Boolean>,
    viewModel: ChatAppViewModel,
) {
    val messageSelectInitiated by viewModel.messageSelectInitiated.collectAsState()
    Log.d("isMessageSelect", "SubMessageItem: $messageSelectInitiated")
    val isOwnMessage = remember { mutableStateOf(false) }

    val auth: FirebaseAuth = Firebase.auth
    if (message.sender != (auth.currentUser?.uid ?: "")) {
        isOwnMessage.value = false
    } else {
        isOwnMessage.value = true
    }
    val messageSentStatus by viewModel.messageSentStatus.collectAsState()
    val isAppMinimized by viewModel.isAppMinimized.collectAsState()

    Column(horizontalAlignment = if (isOwnMessage.value) Alignment.End else Alignment.Start) {


//        Text(message.sender)


        // Message Card
        Row(
            modifier = Modifier
                .fillMaxWidth()
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
                ),
            horizontalArrangement = if (isOwnMessage.value) Arrangement.End else Arrangement.Start
        ) {
            Row(
                modifier = Modifier
                    .background(
                        color = if (isOwnMessage.value) Color(0xFF128C7E) else MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomEnd = if (isOwnMessage.value) 4.dp else 16.dp,
                            bottomStart = if (isOwnMessage.value) 16.dp else 4.dp
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = if (isOwnMessage.value) Color(0xFF075E54) else MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.1f
                        ),
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomEnd = if (isOwnMessage.value) 4.dp else 16.dp,
                            bottomStart = if (isOwnMessage.value) 16.dp else 4.dp
                        )
                    )
                    .padding(horizontal = 8.dp, vertical = 8.dp),

                horizontalArrangement = if (isOwnMessage.value) Arrangement.End else Arrangement.Start
            ) {
                Text(
                    message.text,
                    modifier = Modifier
                        .widthIn(max = 250.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                val date = inputFormat.parse(message.timestamp)

                Text(
                    text = outputFormat.format(date!!),
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .widthIn(min = 30.dp),
                    style = MaterialTheme.typography.labelSmall
                )

                if (message.icons == "Edited") {
                    Spacer(modifier = Modifier.width(5.dp))
                    FaIcon(
                        faIcon = FaIcons.PencilAlt,
                        tint = Color.LightGray,
                        size = 15.dp, modifier = Modifier.align(Alignment.Bottom)
                    )
                }
                if (isOwnMessage.value) {
                    if (message.icons == "singleTick") {
                        Spacer(modifier = Modifier.width(5.dp))
                        FaIcon(
                            faIcon = FaIcons.Check,
                            tint = Color.LightGray,
                            size = 15.dp, modifier = Modifier.align(Alignment.Bottom)
                        )
                    } else if (message.icons == "clock") {
                        Spacer(modifier = Modifier.width(5.dp))
                        FaIcon(
                            faIcon = FaIcons.Clock,
                            tint = Color.LightGray,
                            size = 15.dp, modifier = Modifier.align(Alignment.Bottom)
                        )

                    } else if (message.icons == "doubleTickGreen") {
                        Spacer(modifier = Modifier.width(5.dp))
                        FaIcon(
                            faIcon = FaIcons.CheckDouble,
                            tint = Color.Green,
                            size = 15.dp, modifier = Modifier.align(Alignment.Bottom)
                        )
                    } else if (message.icons == "doubleTick") {
                        Spacer(modifier = Modifier.width(5.dp))
                        FaIcon(
                            faIcon = FaIcons.CheckDouble,
                            tint = Color.LightGray,
                            size = 15.dp, modifier = Modifier.align(Alignment.Bottom)
                        )
                    }
                }
            }
        }
        if (messageSentStatus && isOwnMessage.value && message.icons != "doubleTickGreen" && message.icons != "singleTick" && message.icons != "doubleTick") {
            val update = mapOf(
                "icons" to "singleTick"
            )
            viewModel.updateMessageItem(message.messageId, update)
            viewModel.changeMessageSentStatus(false)
        }
        if (!isOwnMessage.value && message.icons != "doubleTickGreen" && !isAppMinimized) {
            val update = mapOf(
                "icons" to "doubleTickGreen"
            )
            viewModel.updateMessageItem(message.messageId, update)
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