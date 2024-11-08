package com.example.mychattingapp.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mychattingapp.ChatsData.ContactDetails
import com.example.mychattingapp.ChatsData.loadContacts
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.User
import com.example.mychattingapp.LocaldbLogics.ViewModel.ChatAppViewModel
import com.example.mychattingapp.NavHost.navigateIfNotFast
import com.example.mychattingapp.ui.theme.MyChattingAppTheme
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

@Composable
//@Preview
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: ChatAppViewModel
) {
    val messageCounterEnabled = remember { mutableStateOf(true) }
    val textFieldValue = remember { mutableStateOf("") }
    val showAddUserUI = remember { mutableStateOf(false) }
    val userList by viewModel.allUsers.observeAsState(emptyList())

    MyChattingAppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBarFun(
                    text = "Chatting App",
                    firstIcon = {
                        FaIcon(
                            faIcon = FaIcons.Whatsapp,
                            size = 35.dp
                        )
                    },
                    color = Color(0xFFc675f1),
                    showDialog = showAddUserUI
                )
            }
        ) { innerPadding ->
            if (showAddUserUI.value) {
                AddUserUI(showDialog = showAddUserUI, textFieldValue = textFieldValue, viewModel = viewModel)
            }

            LazyColumn(
                modifier = Modifier.padding(innerPadding)
            ) {
                items(userList) { contact ->
                    ChatRow(
                        contactName = contact.userName,
                        recentMessage = contact.recentMessage,
                        messageSentTime = contact.messageSentTime,
                        onClick = {
                            navigateIfNotFast {
                                navController.navigate("chat_screen/${contact.id}")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarFun(
    firstIcon: @Composable () -> Unit = {},
    text: String,
    width: Dp = 1.dp,
    body: @Composable () -> Unit = {},
    color: Color,
    showDialog: MutableState<Boolean> = remember { mutableStateOf(false) }

) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    firstIcon()
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = text,
                        fontSize = 26.sp
                    )
                    Spacer(modifier = Modifier.width(width))
                    body()
                }

                IconButton(onClick = {
                    showDialog.value = !showDialog.value
                }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(color)
    )
}

@Composable
fun AddUserUI(
    showDialog: MutableState<Boolean>,
    textFieldValue: MutableState<String>,
    viewModel: ChatAppViewModel
) {
    Dialog(onDismissRequest = { showDialog.value = false }) {
        Surface(
            modifier = Modifier.padding(16.dp),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.background,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Add contact Details below:",
                    fontSize = 25.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = textFieldValue.value,
                    onValueChange = { textFieldValue.value = it },
                    label = {
                        Text("Add Contact")
                    },
                    placeholder = {
                        Text("Enter contact name")
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        viewModel.addUser(
                            user = User(
                                userName = textFieldValue.value,
                                messageSentTime = "12:02",
                                messageCounter = "1",
                                recentMessage = "Hi"

                            )
                        )
                        showDialog.value=false

                    }
                ) {
                    Text("ADD")
                }
            }
        }
    }
}

@Composable
fun ChatRow(
    contactName: String,
    recentMessage: String,
    messageSentTime: String,
    onClick: () -> Unit = {},
    messageCounterEnabled: Boolean = true,
    messageCounterNumber: String = "1"
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(69.dp)
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = contactName,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )

                Row(horizontalArrangement = Arrangement.SpaceAround) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.LightGray
                    )
                    Text(
                        text = recentMessage,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.LightGray
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(
                    text = messageSentTime,
                    color = Color.Green,
                    fontSize = 12.sp
                )

                if (messageCounterEnabled) {
                    Card(
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(Color.Green),
                        modifier = Modifier.size(23.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Green),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = messageCounterNumber,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.inversePrimary,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
