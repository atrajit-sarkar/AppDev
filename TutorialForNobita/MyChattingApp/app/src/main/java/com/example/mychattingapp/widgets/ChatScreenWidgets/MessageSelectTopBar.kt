package com.example.mychattingapp.widgets.ChatScreenWidgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.Message
import com.example.mychattingapp.LocaldbLogics.ViewModel.ChatAppViewModel
import com.example.mychattingapp.widgets.HomeScreenWidgets.TopBarFun
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons


@Composable
fun MessageSelectTopBar(
    selectedChats: String = "1",
    viewModel: ChatAppViewModel,
    messageList: List<Message>,
    onDismiss: () -> Unit
) {
    val showDeleteMessageDialogue = remember {
        mutableStateOf(false)
    }
    TopBarFun(
        firstIcon = {
            IconButton(
                onClick = {
                    viewModel.clearSelectedMessages()

                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        text = selectedChats,
        color = MaterialTheme.colorScheme.background,
        body = {
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                IconButton(
                    onClick = {}

                ) {
                    FaIcon(
                        faIcon = FaIcons.ArrowAltCircleLeftRegular,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                IconButton(
                    onClick = {}

                ) {
                    FaIcon(
                        faIcon = FaIcons.Save,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                IconButton(
                    onClick = {
                        showDeleteMessageDialogue.value = true
                    }

                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "",

                        )
                }


                ShowDeleteMessageDialogue(
                    showDialog = showDeleteMessageDialogue,
                    viewModel = viewModel,
                    messageList = messageList,
                    onDismiss=onDismiss
                )

                IconButton(
                    onClick = {}

                ) {
                    FaIcon(
                        faIcon = FaIcons.ArrowAltCircleRight,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                IconButton(
                    onClick = {}

                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = ""
                    )
                }

            }

        }
    )
}

@Composable
private fun ShowDeleteMessageDialogue(
    showDialog: MutableState<Boolean>,
    viewModel: ChatAppViewModel,
    messageList: List<Message>,
    onDismiss: () -> Unit
) {
    if (showDialog.value) {
        Dialog(onDismissRequest = {
            showDialog.value = false
            onDismiss()
        }) {
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
                        for (message in messageList){
                            viewModel.deleteMessage(message)
                        }
                        showDialog.value = false
                        viewModel.clearSelectedMessages()
                        onDismiss()
                    }) {
                        Text("Yes")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        showDialog.value = false
                        onDismiss()
                    }) {
                        Text("Close")
                    }
                }
            }
        }
    }
}