package com.example.mychattingapp.Screens

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.Message
import com.example.mychattingapp.LocaldbLogics.DAO.Entities.User
import com.example.mychattingapp.LocaldbLogics.ViewModel.ChatAppViewModel
import com.example.mychattingapp.NavHost.navigateIfNotFast
import com.example.mychattingapp.RotatingIcon
import com.example.mychattingapp.ui.theme.MyChattingAppTheme
import com.example.mychattingapp.widgets.HomeScreenWidgets.ChatRow
import com.example.mychattingapp.widgets.HomeScreenWidgets.DeleteUserDialogue
import com.example.mychattingapp.widgets.HomeScreenWidgets.DropDownMenuHomeScreen
import com.example.mychattingapp.widgets.HomeScreenWidgets.TopBarFun
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@OptIn(ExperimentalFoundationApi::class)
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
    val searchinputtext = remember { mutableStateOf("") }
    val isFocused by viewModel.homeScreenSearchBarActiveState.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val userShortedList = remember {
        mutableStateListOf<User>()
    }
    val messageShortedList = remember {
        mutableStateListOf<Message>()
    }


    // Clear the lists before adding new filtered results
    userShortedList.clear()
    for (user in userList) {
        if (user.userName.contains(searchinputtext.value, ignoreCase = true)) {
            userShortedList.add(user)
        }
    }

    messageShortedList.clear()
    for (chat in messageList) {
        if (chat.text.contains(searchinputtext.value, ignoreCase = true)) {
            messageShortedList.add(chat)
        }
    }
//    val activity = LocalContext.current as? Activity
//
//    BackHandler {
//        activity?.moveTaskToBack(true) // Minimize the app
//    }


    MyChattingAppTheme {

        LazyColumn(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight()
        ) {

            if (!isFocused) {

                item {

                    SearchBar(searchinputtext, focusRequester, viewModel, isFocused, focusManager)
                }
            } else {
                stickyHeader {
                    SearchBar(searchinputtext, focusRequester, viewModel, isFocused, focusManager)
                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }
                }
            }


            if (isFocused && searchinputtext.value != "") {
                item {
                    LazyRow(horizontalArrangement = Arrangement.SpaceEvenly) {
                        item {

                            Card(
                                shape = RoundedCornerShape(12.dp),
                                backgroundColor = MaterialTheme.colorScheme.background,
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Row(modifier = Modifier.padding(8.dp)) {
                                    FaIcon(
                                        faIcon = FaIcons.Link,
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        size = 20.dp
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text("Links")
                                }
                            }
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                backgroundColor = MaterialTheme.colorScheme.background,
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Row(modifier = Modifier.padding(8.dp)) {
                                    FaIcon(
                                        faIcon = FaIcons.Image,
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        size = 20.dp
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text("Photos")
                                }
                            }
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                backgroundColor = MaterialTheme.colorScheme.background,
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Row(modifier = Modifier.padding(8.dp)) {
                                    FaIcon(
                                        faIcon = FaIcons.File,
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        size = 20.dp
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text("Documents")
                                }
                            }
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                backgroundColor = MaterialTheme.colorScheme.background,
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Row(modifier = Modifier.padding(8.dp)) {
                                    FaIcon(
                                        faIcon = FaIcons.Video,
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        size = 20.dp
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text("Videos")
                                }
                            }
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                backgroundColor = MaterialTheme.colorScheme.background,
                                modifier = Modifier.padding(5.dp)
                            ) {
                                Row(modifier = Modifier.padding(8.dp)) {
                                    FaIcon(
                                        faIcon = FaIcons.Poll,
                                        tint = MaterialTheme.colorScheme.onBackground,
                                        size = 20.dp
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text("Polls")
                                }
                            }
                        }

                    }
                }

                item {
                    Spacer(Modifier.height(10.dp))
                    Text(
                        "Chats",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                items(userShortedList) { contact ->
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
                            // new logic will be implemented later...........

                        }
                    )


                }

                item {
                    Spacer(Modifier.height(10.dp))
                    Text(
                        "Messages",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                items(messageShortedList) { message ->
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(100.dp)
                            .fillParentMaxWidth()
                            .clickable {
                                navigateIfNotFast {
                                    navController.navigate("chat_screen/${message.chatId}/${message.id}")
                                }


                            },
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(modifier = Modifier.width(200.dp)) {
                            Column {
                                Text(
                                    identifyUserFromAMessage(
                                        users = userList,
                                        message = message
                                    )
                                )
                                Text(
                                    message.text,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )

                            }

                        }
                        Row {
                            Text(
                                message.timestamp,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                        }


                    }

                }
            } else {


                items(userList) { contact ->
                    val messages by viewModel.getMessageById(contact.id).observeAsState(emptyList())
                    if (messages.isNotEmpty()) {

                        contact.recentMessage =
                            if (messages.last().reaction == "") messages.last().text else "You reacted ${messages.last().reaction} to \"${messages.last().text}\""
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

            if ((userList.size > 5 && userShortedList.size>5)) {

                item {
                    Spacer(modifier=Modifier.height(20.dp))
                    HorizontalDivider(color = Color(0xFF77777A))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(30.dp)
                            .height(30.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FaIcon(
                            faIcon = FaIcons.LockOpen,
                            tint = MaterialTheme.colorScheme.onBackground,
                            size = 15.dp
                        )
                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            "Chats are open and leaking to our server",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                    Spacer(modifier = Modifier.height(120.dp))
                }
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
fun SearchBar(
    searchinputtext: MutableState<String>,
    focusRequester: FocusRequester,
    viewModel: ChatAppViewModel,
    isFocused: Boolean,
    focusManager: FocusManager
) {
    TextField(textStyle = TextStyle(fontSize = 18.sp),
        value = searchinputtext.value, onValueChange = { searchinputtext.value = it },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                viewModel.changeHomeScreenSearchBarActiveState(focusState.isFocused)
            },
        shape = RoundedCornerShape(70.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ), placeholder = { Text("ASK BAJIRAO OR SEARCH", color = Color(0xABA7ADA7)) },
        leadingIcon = {
            Row(verticalAlignment = Alignment.CenterVertically) {

                if (isFocused) {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                        searchinputtext.value = ""
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = ""
                        )
                    }

                }
                RotatingIcon()
            }
        },
        trailingIcon = {
            if (
                isFocused
            ) FaIcon(
                faIcon = FaIcons.Facebook,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    )
    Spacer(modifier = Modifier.height(10.dp))
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


fun identifyUserFromAMessage(
    users: List<User>,
    message: Message
): String {
    for (user in users) {
        if (user.id == message.chatId) {
            return user.userName
        }
    }
    return "" // Return an empty string if no match is found
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

