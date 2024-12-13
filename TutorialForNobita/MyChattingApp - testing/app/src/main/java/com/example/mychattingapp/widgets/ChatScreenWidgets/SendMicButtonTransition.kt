package com.example.mychattingapp.widgets.ChatScreenWidgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.example.mychattingapp.FireBaseLogics.addMessageToFirestore
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.Message
import com.example.mychattingapp.LocaldbLogics.ViewModel.ChatAppViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RowScope.Send_MicButtonTransition(
    textFiledValue: MutableState<String>,
    viewModel: ChatAppViewModel,
    sendIcon: MutableState<Boolean>,
    MicIcon: MutableState<Boolean>,
    uid: String
) {
    val isEditingInitiated by viewModel.messageEditingInitiated.collectAsState()
    val selectedMessages by viewModel.selectedMessages.collectAsState()
    val isEditingGoingOn = remember {
        mutableStateOf(false)
    }
    val auth: FirebaseAuth = Firebase.auth
    if (isEditingInitiated) {
        textFiledValue.value = selectedMessages[0].text
        viewModel.isEditingInitiated(false)
        isEditingGoingOn.value = true
    }else{
        isEditingGoingOn.value = false
    }
    IconButton(
        colors = IconButtonDefaults.iconButtonColors(Color.Green),
        onClick = {
            val currentTime = SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", // Format to include full date and time
                Locale.getDefault()
            ).format(Date())


            if (textFiledValue.value.isNotBlank()) {
                if (!isEditingGoingOn.value) {

                    auth.currentUser?.uid?.let {
                        Message(
                            sender = it,
                            text = textFiledValue.value,
                            timestamp = currentTime,
                            chatId = -1,
                            receiver = uid,
                            reaction = ""
                        )
                    }?.let {
                        addMessageToFirestore(
                            it
                        )
                    } // Add message at the top
                } else {
                    val updates = mapOf(
                        "text" to textFiledValue.value,
                        "timestamp" to currentTime,
                        "icons" to "Edited"
                    )
                    viewModel.updateMessageItem(selectedMessages[0].messageId, updates)
                    isEditingGoingOn.value = false
                    viewModel.clearSelectedMessages()
                }

                textFiledValue.value = "" // Clear the input
                // Scroll to the newest message
//                                Log.d("message", "ChatScreen: $messageList")

            }

        }
    ) {
        AnimatedVisibility(
            visible = sendIcon.value,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) +
                    scaleIn(
                        initialScale = 0.8f,
                        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                    ),
            exit = fadeOut(
                animationSpec = tween(
                    durationMillis = 200,
                    easing = LinearOutSlowInEasing
                )
            ) +
                    scaleOut(
                        targetScale = 0.8f,
                        animationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing)
                    )
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = null,
                tint = Color.Black
            )
        }

        AnimatedVisibility(
            visible = MicIcon.value,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) +
                    scaleIn(
                        initialScale = 0.8f,
                        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                    ),
            exit = fadeOut(
                animationSpec = tween(
                    durationMillis = 200,
                    easing = LinearOutSlowInEasing
                )
            ) +
                    scaleOut(
                        targetScale = 0.8f,
                        animationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing)
                    )
        ) {
            FaIcon(
                faIcon = FaIcons.Microphone
            )
        }


    }
}