package com.example.mycalculator.Screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Space
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mycalculator.Data.HomeScreenData.DummyContacts
import com.example.mycalculator.Data.HomeScreenData.viewModel.ContactViewModel
import com.example.mycalculator.Navigation.navigateIfNotFast
import com.example.mycalculator.components.HomeScreenComponents.AddContactDialog
import com.example.mycalculator.components.HomeScreenComponents.ContactCard
import com.example.mycalculator.components.HomeScreenComponents.ProfessionalTopAppBar
import com.example.mycalculator.components.HomeScreenComponents.SearchBar

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
@Composable
fun HomeScreen(
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    viewModel: ContactViewModel,
    navController: NavController
) {
    // Required variables............
    val textFieldValue = remember {
        mutableStateOf("")
    }

    var dummyContacts = viewModel.allContacts.collectAsState(initial = emptyList())
    var dummyGroups = viewModel.allGroups.collectAsState(initial = emptyList())
    val focusRequester = remember {
        FocusRequester()
    }
    // The variable isFocused is storing the state of the search bar either it is active or not.......
    val isFocused = remember {
        mutableStateOf(false)
    }
    val focusManager = LocalFocusManager.current


    //Logic to Store Matched Contacts in the dummy Contact List..........
    val filteredContacts = dummyContacts.value.filter { contact ->
        contact.name.contains(textFieldValue.value, ignoreCase = true) ||
                contact.phoneNumber.contains(textFieldValue.value, ignoreCase = true) ||
                contact.email.contains(textFieldValue.value, ignoreCase = true) ||
                contact.dob.contains(textFieldValue.value, ignoreCase = true)
    }

    val showAddContactDialogue = remember {
        mutableStateOf(false)
    }

    val name = remember {
        mutableStateOf("")
    }
    val email = remember {
        mutableStateOf("")
    }
    val phoneNumber = remember {
        mutableStateOf("")
    }
    val dob = remember {
        mutableStateOf("")
    }



    Scaffold(
        topBar = {
            if (!isFocused.value)
                ProfessionalTopAppBar(title = "Contacts")
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = Color(0xFF009688),
                onClick = {
                    showAddContactDialogue.value = true
                },
                modifier = Modifier.offset(x = (-20).dp, y = (-100).dp)
            ) {
                Icon(
                    imageVector = if (!showAddContactDialogue.value) Icons.Default.Add else Icons.Default.AccountBox,
                    contentDescription = "add",
                    modifier = Modifier.size(40.dp)
                )

            }

        }
    ) { innerPadding ->
        if (showAddContactDialogue.value) {

            AddContactDialog(
                onDismiss = {
                    showAddContactDialogue.value = false
                },
                name = name,
                email = email,
                phoneNumber = phoneNumber,
                dob = dob,
            ) {
                viewModel.addUser(
                    DummyContacts(
                        name = name.value,
                        phoneNumber = phoneNumber.value,
                        email = email.value,
                        dob = dob.value,
                        groupId = -1
                    )
                )
            }
        }


        // Lazy Column Structure to store dummy data and the search bar..........

        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
                .padding(top = 10.dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(10.dp),
            horizontalAlignment = Alignment.Start
        )
        {
            // If search bar is not active then put it as a normal item in the lazy column so that if will be scrolled and gets hidden and not take unnecessary space in ui......
            if (!isFocused.value) {
                item {

                    SearchBar(
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


//            // A suitable space before starting the main list after the searchbar
//            item {
//                Spacer(modifier = Modifier.height(10.dp))
//            }

            item {
                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    items(dummyGroups.value) { dummyGroup ->
                        val groupContacts =
                            viewModel.getGroupContacts(dummyGroup.id).collectAsState(emptyList())
                        Card(
                            modifier = Modifier
                                .widthIn(min = 10.dp, max = 70.dp)
                                .combinedClickable(
                                    onLongClick = {
                                        viewModel.deleteGroup(dummyGroup.id)
                                        for (groupContact in groupContacts.value) {
                                            viewModel.updateUser(
                                                groupContact.copy(groupId = -1)
                                            )
                                        }
                                    }
                                ) {
                                    navigateIfNotFast {
                                        navController.navigate("group_screen/${dummyGroup.id}")
                                    }


                                },
                            elevation = CardDefaults.cardElevation(20.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(5.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    dummyGroup.name,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                            }
                        }

                    }

                    item {
                        Card(
                            onClick = {
                                navigateIfNotFast {

                                    navController.navigate("group_creation_screen")
                                }

                            },
                            modifier = Modifier.width(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .align(Alignment.CenterHorizontally)
                            )
                        }

                    }

                }
            }


            // Main list of contact
            items(
                items = if (isFocused.value && textFieldValue.value.isNotBlank()) filteredContacts else dummyContacts.value
            ) { contact ->
                ContactCard(
                    contact = contact,
                    viewModel = viewModel,
                    showGroupName = true
                )
                Log.d("contactGroupId", "HomeScreen: ${contact.groupId}")


            }

            if (dummyContacts.value.size > 5) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.onBackground)
                    Spacer(modifier = Modifier.height(200.dp))
                }
            }


        }
    }

}





