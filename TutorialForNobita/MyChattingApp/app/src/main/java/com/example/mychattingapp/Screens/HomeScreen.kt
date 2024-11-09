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
import androidx.compose.material.icons.filled.Add
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
import com.example.mychattingapp.widgets.HomeScreenWidgets.AddUserUI
import com.example.mychattingapp.widgets.HomeScreenWidgets.ChatRow
import com.example.mychattingapp.widgets.HomeScreenWidgets.DeleteUserDialogue
import com.example.mychattingapp.widgets.HomeScreenWidgets.DropDownMenuHomeScreen
import com.example.mychattingapp.widgets.HomeScreenWidgets.TopBarFun
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

@Composable
//@Preview
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: ChatAppViewModel
) {
    val messageCounterEnabled = remember { mutableStateOf(true) }
    val showDropDown = remember { mutableStateOf(false) }
    val userList by viewModel.allUsers.observeAsState(emptyList())
    val showDeleteUserDialoge = remember {
        mutableStateOf(false)
    }
    val selectedContact = remember { mutableStateOf<User?>(null) }

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
                    navController = navController,
                    body = {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        ) {

                            IconButton(
                                onClick = {
                                    // Drop Down Menu........
                                    showDropDown.value = !showDropDown.value

                                },
                                modifier = Modifier.size(25.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = null
                                )
                            }
                            DropDownMenuHomeScreen(
                                expanded = showDropDown,
                                navController = navController,
                                viewModel = viewModel
                            )

                        }
                    }
                )
            }
        ) { innerPadding ->

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
                        },
                        onLongPress = {
                            selectedContact.value = contact
                            showDeleteUserDialoge.value = true

                        }
                    )

                }
            }

            if (showDeleteUserDialoge.value) {
                selectedContact.value?.let { contact ->
                    DeleteUserDialogue(
                        showDialog = showDeleteUserDialoge,
                        contactName = contact.userName,
                        viewModel = viewModel,
                        user = contact
                    )
                }
            }


        }
    }
}


