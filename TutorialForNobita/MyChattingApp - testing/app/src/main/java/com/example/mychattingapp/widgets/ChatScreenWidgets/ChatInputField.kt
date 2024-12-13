package com.example.mychattingapp.widgets.ChatScreenWidgets

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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


@Composable
fun ChatInputField(
    textFiledValue: MutableState<String>,
    sendIcon: MutableState<Boolean>,
    MicIcon: MutableState<Boolean>,
    viewModel: ChatAppViewModel,
    uid:String
) {
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


