package com.example.mycalculator.Screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mycalculator.Data.HomeScreenData.DummyContacts
import com.example.mycalculator.Data.HomeScreenData.viewModel.ContactViewModel
import com.example.mycalculator.Navigation.navigateIfNotFast
import com.example.mycalculator.components.HomeScreenComponents.ConfirmationDialog
import com.example.mycalculator.components.HomeScreenComponents.ContactCard
import com.example.mycalculator.components.HomeScreenComponents.ProfessionalTopAppBar
import com.example.mycalculator.components.HomeScreenComponents.SearchBar
import com.example.mycalculator.ui.theme.MyCalculatorTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GroupScreen(
    navController: NavController,
    viewModel: ContactViewModel,
    groupId: String
) {
    val groupContacts = viewModel.getGroupContacts(groupId.toInt()).collectAsState(emptyList())
    val allGroups = viewModel.allGroups.collectAsState(emptyList())

    val isFocused = remember {
        mutableStateOf(false)
    }
    val textFieldValue = remember {
        mutableStateOf("")
    }
    val focusRequester = remember {
        androidx.compose.ui.focus.FocusRequester()
    }
    val focusManager = LocalFocusManager.current


    //Logic to Store Matched Contacts in the dummy Contact List..........
    val filteredContacts = groupContacts.value.filter { contact ->
        contact.name.contains(textFieldValue.value, ignoreCase = true) ||
                contact.phoneNumber.contains(textFieldValue.value, ignoreCase = true) ||
                contact.email.contains(textFieldValue.value, ignoreCase = true) ||
                contact.dob.contains(textFieldValue.value, ignoreCase = true)
    }
    val Targetgroup = allGroups.value.filter { group -> group.id == groupId.toInt() }
    val showRemoveConformation = remember {
        mutableStateOf(false)
    }
    val selectedContactForRemoval = remember { mutableStateOf<DummyContacts?>(null) }

    MyCalculatorTheme {
        Scaffold(
            topBar = {
                for (group in allGroups.value) {
                    if (group.id == groupId.toInt()) {
                        if (!isFocused.value) {
                            GroupScreenProfessionalTopAppBar(title = group.name,
                                onNavigationIconClick = {
                                    navigateIfNotFast {
                                        navController.navigate("home_screen")
                                    }
                                })
                        }
                    }
                }

            },
            floatingActionButton = {
                FloatingActionButton(
                    containerColor = Color(0xFF009688),
                    onClick = {
                        navigateIfNotFast {
                            navController.navigate("next_layer_contact_selection_screen/$groupId")

                        }

                    },
                    modifier = Modifier.offset(x = (-20).dp, y = (-100).dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "add",
                        modifier = Modifier.size(40.dp)
                    )

                }

            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {

                // Show the confirmation dialog only once
                if (showRemoveConformation.value && selectedContactForRemoval.value != null) {
                    ConfirmationDialog(
                        title = if (Targetgroup.isNotEmpty()) "Remove Contact From ${Targetgroup[0].name}" else "",
                        message = "Are you sure you want to remove this contact?",
                        onYesClick = {
                            selectedContactForRemoval.value?.let { contact ->
                                viewModel.updateUser(
                                    contacts = contact.copy(groupId = -1)
                                )
                            }
                            showRemoveConformation.value = false
                            selectedContactForRemoval.value = null
                        },
                        onNoClick = {
                            showRemoveConformation.value = false
                            selectedContactForRemoval.value = null
                        },
                        onDismiss = {
                            showRemoveConformation.value = false
                            selectedContactForRemoval.value = null
                        }
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(10.dp),
                    horizontalAlignment = Alignment.Start
                ) {

                    // If search bar is not active then put it as a normal item in the lazy column so that if will be scrolled and gets hidden and not take unnecessary space in ui......
                    if (!isFocused.value) {
                        item {
                            SearchBar(
                                label = if (Targetgroup.isNotEmpty()) "Search in ${Targetgroup[0].name}" else "",
                                textFieldValue = textFieldValue,
                                focusRequester = focusRequester,
                                isFocused = isFocused,
                                focusManager = focusManager
                            )

                        }

                    } else {  // When search bar is active put it inside stickyHeader so that user can scroll the shorted list without even making the search bar hidden for convenience......
                        stickyHeader {

                            // We created a card so that the supporting text and all other searchbar component don't overlap with the scrollable list...............
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
                                shape = RectangleShape
                            ) {
                                Spacer(modifier = Modifier.height(10.dp))

                                SearchBar(
                                    label = if (Targetgroup.isNotEmpty()) "Search in ${Targetgroup[0].name}" else "",
                                    textFieldValue = textFieldValue,
                                    focusRequester = focusRequester,
                                    isFocused = isFocused,
                                    focusManager = focusManager
                                )
                                Spacer(modifier = Modifier.height(10.dp))

                            }
                            // This ensures that when the search bar is clicked and recomposed it doesn't forgets its active state so request the active state again when the search bar recomposed.....
                            LaunchedEffect(Unit) {
                                focusRequester.requestFocus()
                            }
                        }


                    }

                    items(
                        items = if (isFocused.value && textFieldValue.value.isNotBlank()) filteredContacts else groupContacts.value
                    ) { groupContact ->
                        ContactCard(
                            contact = groupContact,
                            viewModel = viewModel,
                            removeFunction = {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "remove",
                                    tint = Color.Red,
                                    modifier = Modifier.clickable {
                                        selectedContactForRemoval.value = groupContact
                                        showRemoveConformation.value = true
                                    }
                                )

                            }
                        )

                    }

                }

            }
        }
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun GroupScreenProfessionalTopAppBar(
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
//            IconButton(onClick = onActionClick) {
//                Icon(
//                    imageVector = Icons.Default.Search,
//                    contentDescription = "Search",
//                    tint = MaterialTheme.colorScheme.onPrimary
//                )
//            }
            IconButton(onClick = { /* Another Action */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More Options",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}