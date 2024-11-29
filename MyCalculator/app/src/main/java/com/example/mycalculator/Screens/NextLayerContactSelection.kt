package com.example.mycalculator.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mycalculator.Data.HomeScreenData.DummyContacts
import com.example.mycalculator.Data.HomeScreenData.viewModel.ContactViewModel
import com.example.mycalculator.Navigation.navigateIfNotFast
import com.example.mycalculator.components.HomeScreenComponents.ContactCard
import com.example.mycalculator.components.HomeScreenComponents.SearchBar
import com.example.mycalculator.ui.theme.MyCalculatorTheme

@Composable
fun NextLayerContactSelectionScreen(
    viewModel: ContactViewModel,
    navController: NavController,
    groupId: String
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
    var textFieldValue = remember {
        mutableStateOf("")
    }
    val focusRequester = remember {
        FocusRequester()
    }

    //Logic to Store Matched Contacts in the dummy Contact List..........
    val filteredContacts = dummyContacts.value.filter { contact ->
        contact.name.contains(textFieldValue.value, ignoreCase = true) ||
                contact.phoneNumber.contains(textFieldValue.value, ignoreCase = true) ||
                contact.email.contains(textFieldValue.value, ignoreCase = true) ||
                contact.dob.contains(textFieldValue.value, ignoreCase = true)
    }

    MyCalculatorTheme {
        val Targetgroup = dummyGroups.value.filter { group -> group.id == groupId.toInt() }
        Scaffold(
            topBar = {
                if (!showSearchBar.value) {


                    ContactSelectionScreenProfessionalTopAppBar(title = if (Targetgroup.isNotEmpty()) "Add to ${Targetgroup[0].name}" else "Add to list:",
                        onNavigationIconClick = {
                            navigateIfNotFast {
                                navController.popBackStack()

                            }
                        },
                        onActionClick = {
                            showSearchBar.value = true
                        }
                    )
                }

            },
            floatingActionButton = {
                FloatingActionButton(
                    containerColor = Color(0xFF009688),
                    onClick = {
                        for (contact in selectedContacts.value) {
                            viewModel.updateUser(
                                contacts = DummyContacts(
                                    id = contact.id,
                                    name = contact.name,
                                    phoneNumber = contact.phoneNumber,
                                    email = contact.email,
                                    dob = contact.dob,
                                    groupId = if (Targetgroup.isNotEmpty()) Targetgroup[0].id else -1
                                )
                            )

                        }
                        navigateIfNotFast {
                            navController.navigate("group_screen/${groupId}")

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
                            textFieldValue = textFieldValue,
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
                        Text(text = if (Targetgroup.isNotEmpty()) "You are adding to \" ${Targetgroup[0].name} \"" else "You are adding to list:")

                    }
                HorizontalDivider(color = Color.LightGray)

                LazyColumn() {
                    // Main list of contact
                    items(
                        items = if (isFocused.value && textFieldValue.value.isNotBlank()) filteredContacts.filter { it !in selectedContacts.value } else dummyContacts.value.filter { it !in selectedContacts.value }
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