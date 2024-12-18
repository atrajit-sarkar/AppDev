package com.example.mychattingapp.widgets.ChatScreenWidgets

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mychattingapp.LocaldbLogics.ViewModel.ChatAppViewModel
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ChatInputField(
    textFiledValue: MutableState<String>,
    sendIcon: MutableState<Boolean>,
    MicIcon: MutableState<Boolean>,
    viewModel: ChatAppViewModel,
    uid: String
) {
    var debounceJob: Job? by remember { mutableStateOf(null) }
    val coroutineScope = rememberCoroutineScope()
    val currentUser = viewModel.filterUser(viewModel.currentUserId.value).collectAsState()

    BottomAppBar(
        containerColor = Color.Transparent,
        modifier = Modifier.wrapContentHeight()
    ) {
        val showCameraIcon = remember {
            mutableStateOf(false)
        }
        BasicTextField(
            value = textFiledValue.value,
            onValueChange = {
                textFiledValue.value = it
                Log.d("Typing", "ChatInputField: Typing......")
                val updateTyping = mapOf(
                    "activeStatus" to "Online,Typing...."
                )
                if (currentUser.value[0].activeStatus != "Online,Typing....") {
                    viewModel.updateUserItem(viewModel.currentUserId.value, updateTyping)
                }

                // Cancel the previous job if it exists
                debounceJob?.cancel()
                debounceJob = coroutineScope.launch {
                    // Wait for the debounce time (e.g., 500 ms)
                    delay(500)
                    Log.d("Typing", "ChatInputField: Typing Finished.")
                    val updateOnline = mapOf(
                        "activeStatus" to "Online"
                    )
                    viewModel.updateUserItem(viewModel.currentUserId.value, updateOnline)
                }
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
                .heightIn(min = 30.dp, max = 100.dp), // Set minimum and maximum height
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {

                        },
                        modifier = Modifier.size(24.dp)
                    ) {

                        FaIcon(
                            faIcon = FaIcons.StickyNote,
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .width(169.dp)
                            .align(Alignment.CenterVertically)
                    ) {
                        // Stack innerTextField and placeholder
                        if (textFiledValue.value == "") {
                            Text("Message", color = Color.Gray)
                        }
                        innerTextField() // Cursor will appear at the start of the input
                    }

                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .padding(horizontal = 0.dp)
                            .width(89.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        IconButton(
                            onClick = {
                                // Handle attachment click
                            },
                            modifier = Modifier.size(24.dp)
                        ) {
                            FaIcon(
                                faIcon = FaIcons.Paperclip,
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(20.dp))

                        // Toggle visibility of the camera icon based on the text field's content
                        if (textFiledValue.value == "") {
                            showCameraIcon.value = true
                        } else {
                            showCameraIcon.value = false
                        }

                        AnimatedVisibility(visible = showCameraIcon.value) {
                            IconButton(
                                onClick = {
                                    // Handle camera icon click
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                FaIcon(
                                    faIcon = FaIcons.Camera,
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
        )

        // Mic to Send button transition logic:
        if (textFiledValue.value != "") {
            sendIcon.value = true
            MicIcon.value = false
        } else {
            MicIcon.value = true
            sendIcon.value = false
        }
        Send_MicButtonTransition(
            textFiledValue,
            viewModel = viewModel,
            sendIcon,
            MicIcon,
            uid
        )


    }
}


