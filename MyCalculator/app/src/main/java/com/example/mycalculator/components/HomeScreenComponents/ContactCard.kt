package com.example.mycalculator.components.HomeScreenComponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.example.mycalculator.Data.HomeScreenData.DummyContacts
import com.example.mycalculator.Data.HomeScreenData.viewModel.ContactViewModel
import java.nio.file.WatchEvent

// Contact Card Function to design the contact display View..................
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactCard(
    removeFunction: @Composable () -> Unit = {},
    contact: DummyContacts,
    viewModel: ContactViewModel,
    showGroupName: Boolean =false,
    onClick: () -> Unit = {},
) {
    val showMoreDetails = remember {
        mutableStateOf(false)
    }

    val showDeleteContactAlertDialog = remember {
        mutableStateOf(false)
    }

    val selectedContactName = remember {
        mutableStateOf(contact.name)
    }

    val selectedContactEmail = remember {
        mutableStateOf(contact.email)
    }

    val selectedContactPhoneNumber = remember {
        mutableStateOf(contact.phoneNumber)
    }

    val selectedContactDob = remember {
        mutableStateOf(contact.dob)
    }

    val contactEditingEnabled = remember {
        mutableStateOf(false)
    }

    val allGroups = viewModel.allGroups.collectAsState(initial = emptyList())
    val Targetgroup = allGroups.value.filter { group -> group.id == contact.groupId }

    if (contactEditingEnabled.value) {

        EditContactDialog(
            contact = contact,
            onDismiss = { contactEditingEnabled.value = false },
            name = selectedContactName,
            email = selectedContactEmail,
            phoneNumber = selectedContactPhoneNumber,
            dob = selectedContactDob,
            onSave = {
                viewModel.updateUser(
                    DummyContacts(
                        id = contact.id,
                        name = selectedContactName.value,
                        email = selectedContactEmail.value,
                        phoneNumber = selectedContactPhoneNumber.value,
                        dob = selectedContactDob.value,
                        groupId = contact.groupId
                    )
                )
            }

        )
    }


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(40.dp))
            .border(
                1.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(40.dp)
            )
            .combinedClickable(onLongClick = {
                showDeleteContactAlertDialog.value = true

            },
                onDoubleClick = {
                    contactEditingEnabled.value = true

                }) {
                onClick()

            }


    ) {

        if (showDeleteContactAlertDialog.value) {
            ConfirmationDialog(
                title = "Delete Contact",
                message = "Are you sure you want to delete this contact?",
                onYesClick = {
                    viewModel.deleteUser(contact.id)
                    showDeleteContactAlertDialog.value = false
                },
                onNoClick = {
                    showDeleteContactAlertDialog.value = false


                },
                onDismiss = {
                    showDeleteContactAlertDialog.value = false
                }
            )
        }
        Row(modifier = if (!showMoreDetails.value) Modifier.widthIn(max = 220.dp) else Modifier) {
            Spacer(modifier = Modifier.width(20.dp))
            Icon(
                imageVector = Icons.Default.AccountBox,
                contentDescription = "contact",
                modifier = Modifier
                    .size(55.dp)
                    .padding(5.dp)
            )
            Column(modifier = Modifier.padding(5.dp)) {
                Row {
                    Text(text = contact.name)
                    if (Targetgroup.isNotEmpty() && showGroupName) {


                        Spacer(modifier = Modifier.width(10.dp))
                        Card(
                            modifier = Modifier.widthIn(min = 10.dp, max = 50.dp),
                            elevation = CardDefaults.cardElevation(20.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
                            colors = CardDefaults.cardColors(Color(0xFF892E9B))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = if (Targetgroup.isNotEmpty()) Targetgroup[0].name else "",
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                            }
                        }
                    }

                }
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxHeight()
                ) {

                    Text(
                        text = if (showMoreDetails.value) "Show Less details" else "Show More details...",
                        style = MaterialTheme.typography.labelSmall
                    )
                    Icon(
                        imageVector = if (!showMoreDetails.value) Icons.Default.ArrowDropDown else Icons.Default.KeyboardArrowUp,
                        contentDescription = "arrow",
                        modifier = Modifier.clickable {
                            showMoreDetails.value = !showMoreDetails.value

                        }
                    )

                }
                AnimatedVisibility(visible = showMoreDetails.value) {
                    Column {
                        Text(text = "Phone: ${contact.phoneNumber}")
                        Text(text = "Email: ${contact.email}")
                        Text(text = "DOB: ${contact.dob}")
                    }

                }

            }
        }

        AnimatedVisibility(visible = !showMoreDetails.value) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "edit",
                    modifier = Modifier.clickable {
                        contactEditingEnabled.value = true
                    }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier.clickable {
                        showDeleteContactAlertDialog.value = true
                    }
                )
                Spacer(modifier = Modifier.width(12.dp))
                removeFunction()
            }
        }

    }

}