package com.example.mycalculator.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mycalculator.Data.GroupScreenData.ContactGroup
import com.example.mycalculator.Data.HomeScreenData.DummyContacts
import com.example.mycalculator.Data.HomeScreenData.viewModel.ContactViewModel
import com.example.mycalculator.Navigation.navigateIfNotFast
import com.example.mycalculator.components.HomeScreenComponents.ContactCard
import com.example.mycalculator.components.HomeScreenComponents.SearchBar
import com.example.mycalculator.ui.theme.MyCalculatorTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun ContactSelectionScreen(
    viewModel: ContactViewModel,
    navController: NavController,
    textFieldValue: String = ""
) {
    var dummyContacts = viewModel.allContacts.collectAsState(initial = emptyList())
    var selectedContacts = viewModel.selectedContactList.collectAsState()
    var dummyGroups = viewModel.allGroups.collectAsState(initial = emptyList())


    var showSearchBar = remember {
        mutableStateOf(false)
    }
    var isFocused = remember {
        mutableStateOf(false)
    }
    var SearchFieldValue = remember {
        mutableStateOf("")
    }
    val focusRequester = remember {
        FocusRequester()
    }

    //Logic to Store Matched Contacts in the dummy Contact List..........
    val filteredContacts = dummyContacts.value.filter { contact ->
        contact.name.contains(SearchFieldValue.value, ignoreCase = true) ||
                contact.phoneNumber.contains(SearchFieldValue.value, ignoreCase = true) ||
                contact.email.contains(SearchFieldValue.value, ignoreCase = true) ||
                contact.dob.contains(SearchFieldValue.value, ignoreCase = true)
    }


    MyCalculatorTheme {
        Scaffold(
            topBar = {
                ContactSelectionScreenProfessionalTopAppBar(title = "Add to list",
                    onNavigationIconClick = {
                        navigateIfNotFast {

                            navController.popBackStack()
                        }
                    },
                    onActionClick = {
                        showSearchBar.value = true
                    })
            },
            floatingActionButton = {
                FloatingActionButton(
                    containerColor = Color(0xFF009688),
                    onClick = {

                        if (selectedContacts.value.isNotEmpty()) {

                            for (contact in selectedContacts.value) {
                                viewModel.updateUser(
                                    contacts = DummyContacts(
                                        id = contact.id,
                                        name = contact.name,
                                        phoneNumber = contact.phoneNumber,
                                        email = contact.email,
                                        dob = contact.dob,
                                        groupId = if (dummyGroups.value.isNotEmpty()) dummyGroups.value.last().id else -1
                                    )
                                )
                            }
                        }

                        navigateIfNotFast {
                            navController.navigate("home_screen")

                        }
                        viewModel.clearSelectedContactList()


                    },
                    modifier = Modifier.offset(x = (-20).dp, y = (-100).dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "add",
                        modifier = Modifier.size(40.dp)
                    )

                }

            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Row(modifier = Modifier.padding(all = 10.dp)) {

                    if (showSearchBar.value) {
                        SearchBar(
                            textFieldValue = SearchFieldValue,
                            focusRequester = focusRequester,
                            isFocused = isFocused,
                            showSearchBar = showSearchBar,
                            focusManager = LocalFocusManager.current
                        )

                        LaunchedEffect(Unit) {
                            focusRequester.requestFocus()
                        }
                    }
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp
                    ),
                    modifier = Modifier.padding(10.dp)
                ) {
                    items(selectedContacts.value) { selectedContact ->
                        SelectedContactLayOut(viewModel, selectedContact)

                    }

                }
                if (selectedContacts.value.isEmpty())
                    Row(
                        modifier = Modifier
                            .height(110.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Your selected contacts will be shown here")

                    }
                HorizontalDivider(color = Color.LightGray)

                LazyColumn() {
                    // Main list of contact
                    items(
                        items = if (isFocused.value && SearchFieldValue.value.isNotBlank()) filteredContacts.filter { it !in selectedContacts.value } else dummyContacts.value.filter { it !in selectedContacts.value }
                    ) { contact ->
                        if (contact.groupId == -1) {

                            ContactCard(
                                contact = contact,
                                viewModel = viewModel,
                                onClick = {
                                    viewModel.addSelectedContact(contact)

                                }
                            )
                        } else {
                            for (group in dummyGroups.value) {
                                if (group.id == contact.groupId) {
                                    Text(
                                        "Already in Group ${group.name}",
                                        modifier = Modifier.padding(horizontal = 10.dp)
                                    )
                                }
                            }
                            Card(
                                colors = CardDefaults.cardColors(Color.LightGray),
                                modifier = Modifier.padding(top = 5.dp),
                                shape = RectangleShape
                            ) {
                                ContactCard(
                                    contact = contact,
                                    viewModel = viewModel,
                                    onClick = {

                                    }
                                )
                            }
                        }


                    }
                }
            }

        }
    }

}


private fun OnClick(
    coroutineScope: CoroutineScope,
    selectedContacts: State<List<DummyContacts>>,
    viewModel: ContactViewModel,
    textFieldValue: String
) {

}

@Composable
fun SelectedContactLayOut(
    viewModel: ContactViewModel,
    selectedContact: DummyContacts
) {
    Column(modifier = Modifier.width(70.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "",
                modifier = Modifier
                    .clickable {
                        viewModel.removeSelectedContact(selectedContact)
                    }
                    .size(20.dp)
            )
        }

        Card(
            shape = CircleShape,
            modifier = Modifier.size(70.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
            )

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = selectedContact.name,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactSelectionScreenProfessionalTopAppBar(
    title: String,
    onNavigationIconClick: () -> Unit = {},
    onActionClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
//            IconButton(onClick = { /* Another Action */ }) {
//                Icon(
//                    imageVector = Icons.Default.MoreVert,
//                    contentDescription = "More Options",
//                    tint = MaterialTheme.colorScheme.onPrimary
//                )
//            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}




