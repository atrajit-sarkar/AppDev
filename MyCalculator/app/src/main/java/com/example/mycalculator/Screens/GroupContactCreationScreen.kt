package com.example.mycalculator.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mycalculator.Data.GroupScreenData.ContactGroup
import com.example.mycalculator.Data.HomeScreenData.viewModel.ContactViewModel
import com.example.mycalculator.Navigation.navigateIfNotFast
import kotlinx.coroutines.launch

@Composable
fun GroupContactCreationScreen(
    navController: NavController,
    viewModel: ContactViewModel
) {
    val textFieldValue = remember {
        mutableStateOf("")
    }



    MaterialTheme {


        Scaffold(
            topBar = {
                GroupContactCreationScreenProfessionalTopAppBar(
                    title = "New Group",
                    onNavigationIconClick = {
                        navigateIfNotFast {
                            navController.popBackStack()

                        }
                    },
                )

            }, bottomBar = {
                BottomAppBar {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            navigateIfNotFast {

                                navController.navigate("contact_selection_screen/${textFieldValue.value}")
                                viewModel.addGroup(ContactGroup(name = textFieldValue.value))
                            }


                        }

                    ) {
                        Text("Add Contacts")
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .imePadding()
                    .padding(5.dp)
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    prefix = {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = "Group Name"
                        )
                    },
                    value = textFieldValue.value,
                    onValueChange = {
                        textFieldValue.value = it
                    },
                    placeholder = {
                        Text(
                            text = "Enter the name of the Group ",
                            fontSize = 12.sp

                        )
                    },
                    label = {
                        Text("Group Name")
                    },
                    supportingText = {
                        Text("Any group you create becomes a filter at the top of your Contact List")
                    }
                )

            }

        }
    }
}


@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupContactCreationScreenProfessionalTopAppBar(
    title: String = "New Group",
    onNavigationIconClick: () -> Unit = {},
    onActionClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent)
    )
}