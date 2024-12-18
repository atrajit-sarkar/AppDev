package com.example.mychattingapp.widgets.ChatScreenWidgets

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
import java.util.TimeZone

@OptIn(ExperimentalFoundationApi::class)
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
    val isEditingGoingOn = remember { mutableStateOf(false) }
    val auth: FirebaseAuth = Firebase.auth

    // Handle message editing
    if (isEditingInitiated) {
        textFiledValue.value = selectedMessages[0].text
        viewModel.isEditingInitiated(false)
        isEditingGoingOn.value = true
    }
    if (selectedMessages.isEmpty()) {
        isEditingGoingOn.value = false
    }

    val sendMessageDropDownExpanded = remember { mutableStateOf(false) }
    val password = remember { mutableStateOf("") }
    val viewOnce = remember { mutableStateOf("") }

    Card(
        colors = CardDefaults.cardColors(Color(color = 0xFF1DA81D)), //whatsapp like color
        modifier = Modifier
            .size(50.dp) // Increase the size slightly for better visuals
            .clip(CircleShape)
            .combinedClickable(
                onLongClick = {
                    if (sendIcon.value) {
                        sendMessageDropDownExpanded.value = true
                    }
                },
                onClick = {
                    val currentTime = SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss",
                        Locale.getDefault()
                    )
                        .apply {
                            timeZone = TimeZone.getTimeZone("GMT")
                        }
                        .format(Date())

                    if (textFiledValue.value.isNotBlank()) {
                        if (!isEditingGoingOn.value) {
                            auth.currentUser?.uid
                                ?.let {
                                    if (uid != it) {
                                        Message(
                                            sender = it,
                                            text = textFiledValue.value,
                                            timestamp = currentTime,
                                            chatId = -1,
                                            receiver = uid,
                                            reaction = "",
                                            icons = "clock",
                                            password = password.value,
                                            viewOnce = viewOnce.value
                                        )
                                    } else {
                                        Message(
                                            sender = it,
                                            text = textFiledValue.value,
                                            timestamp = currentTime,
                                            chatId = -1,
                                            receiver = uid,
                                            reaction = "",
                                            icons = "doubleTickGreen",
                                            password = password.value,
                                            viewOnce = viewOnce.value
                                        )
                                    }
                                }
                                ?.let {
                                    addMessageToFirestore(it, viewModel)
                                }
                        } else {
                            val updates = mapOf(
                                "text" to textFiledValue.value,
                                "icons" to "Edited"
                            )
                            viewModel.updateMessageItem(selectedMessages[0].messageId, updates)
                            viewModel.clearSelectedMessages()
                        }

                        textFiledValue.value = "" // Clear the input
                    }
                    viewOnce.value = ""
                    password.value = ""
                }
            ),
        content = {
            // Use a Box to center the icons
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                AnimatedContent(
                    targetState = sendIcon.value,
                    transitionSpec = {
                        (fadeIn(tween(300)) + scaleIn(
                            initialScale = 0.7f,
                            animationSpec = tween(300)
                        )).togetherWith(
                            fadeOut(tween(200)) + scaleOut(
                                targetScale = 0.7f,
                                animationSpec = tween(200)
                            )
                        )
                    }
                ) { showSendIcon ->
                    if (showSendIcon) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(35.dp)
                                .padding(horizontal = 4.dp)
                        )
                    } else {
                        FaIcon(
                            faIcon = FaIcons.Microphone,
                            tint = Color.Black,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(horizontal = 4.dp)
                        )
                    }
                }
            }
        }
    )

    // Dropdown menu for send options
    DropDownMenuSendMessage(
        expanded = sendMessageDropDownExpanded,
        viewModel = viewModel,
        password = password,
        viewOnce = viewOnce
    )

}
