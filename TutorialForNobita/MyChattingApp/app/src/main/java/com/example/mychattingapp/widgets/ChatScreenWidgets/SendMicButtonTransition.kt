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
import androidx.compose.ui.graphics.Color
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.Message
import com.example.mychattingapp.LocaldbLogics.ViewModel.ChatAppViewModel
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
    chatId:Int
) {
    IconButton(
        colors = IconButtonDefaults.iconButtonColors(Color.Green),
        onClick = {
            if (textFiledValue.value.isNotBlank()) {
                val currentTime =
                    SimpleDateFormat(
                        "HH:mm",
                        Locale.getDefault()
                    ).format(Date())
                viewModel.addMessage(
                    Message(
                        sender = "Atrajit",
                        text = textFiledValue.value,
                        timestamp = currentTime,
                        chatId = chatId
                    )
                ) // Add message at the top
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