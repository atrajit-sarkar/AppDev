package com.example.mychattingapp.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.Message
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.User
import com.example.mychattingapp.LocaldbLogics.ViewModel.ChatAppViewModel
import com.example.mychattingapp.NavHost.navigateIfNotFast
import com.example.mychattingapp.ui.theme.MyChattingAppTheme
import com.example.mychattingapp.widgets.HomeScreenWidgets.ChatRow
import com.example.mychattingapp.widgets.HomeScreenWidgets.DeleteUserDialogue
import com.example.mychattingapp.widgets.HomeScreenWidgets.DropDownMenuHomeScreen
import com.example.mychattingapp.widgets.HomeScreenWidgets.TopBarFun
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import kotlinx.coroutines.launch

@Composable
//@Preview
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: ChatAppViewModel
) {
    val messageCounterEnabled = remember { mutableStateOf(true) }
    val showDeleteUserDialoge = remember {
        mutableStateOf(false)
    }
    val selectedContact = remember { mutableStateOf<User?>(null) }

    // ChatAppViewModel Variables.......
    val userList by viewModel.allUsers.observeAsState(emptyList())
    val messageList by viewModel.allChats.observeAsState(emptyList())

    // Horizontal Scroll Variables........
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()



    MyChattingAppTheme {

        LazyColumn(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight()
        ) {
            items(userList) { contact ->
                val messages by viewModel.getMessageById(contact.id).observeAsState(emptyList())
                if (messages.isNotEmpty()) {

                    contact.recentMessage = messages.last().text
                    contact.messageSentTime = messages.last().timestamp
                } else {
                    contact.recentMessage = ""
                    contact.messageSentTime = ""
                }
                viewModel.updateUser(user = contact)

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

@Composable
fun HomeTopAppBar(
    navController: NavController,
    showDropDown: MutableState<Boolean>,
    viewModel: ChatAppViewModel
) {
    TopBarFun(
        text = "Chatting App",
        firstIcon = {
            FaIcon(
                faIcon = FaIcons.Whatsapp,
                size = 35.dp,
                tint = MaterialTheme.colorScheme.onBackground
            )
        },
        color = MaterialTheme.colorScheme.background,
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

@Composable
fun HomeScreenBottomBar() {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                IconButton(
                    onClick = {

                    }
                ) {

                    FaIcon(
                        faIcon = FaIcons.Rocketchat,
                        tint = Color.LightGray
                    )
                }
                Text("Chats")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                IconButton(
                    onClick = {

                    }
                ) {

                    FaIcon(
                        faIcon = FaIcons.GgCircle,
                        tint = Color.LightGray
                    )
                }
                Text("Updates")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                IconButton(
                    onClick = {

                    }
                ) {

                    FaIcon(
                        faIcon = FaIcons.PeopleArrows,
                        tint = Color.LightGray
                    )
                }
                Text("Communities")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                IconButton(
                    onClick = {

                    }
                ) {

                    FaIcon(
                        faIcon = FaIcons.Phone,
                        tint = Color.LightGray
                    )
                }
                Text("Calls")
            }

        }

    }
}



