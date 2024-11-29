package com.example.mycalculator.components.HomeScreenComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AddContactDialog(
    onDismiss: () -> Unit,
    name: MutableState<String> = remember { mutableStateOf("") },
    email: MutableState<String> = remember { mutableStateOf("") },
    phoneNumber: MutableState<String> = remember { mutableStateOf("") },
    dob: MutableState<String> = remember { mutableStateOf("") },
    isEditingEnabled: MutableState<Boolean> = remember { mutableStateOf(false) },
    onSave: () -> Unit,
) {
    var isError = remember { mutableStateOf(false) }
    var isErrorDob = remember { mutableStateOf(false) }
    val dobRegex = Regex("""^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/(\d{4})$""")

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Add Contact",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text("Name") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = email.value,
                    onValueChange = {
                        email.value = it
                        isError.value = !email.value.contains("@")
                    },
                    label = { Text(if (isError.value) "Invalid Email" else "Email") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email
                    ),
                    isError = isError.value
                )

                OutlinedTextField(
                    value = phoneNumber.value,
                    onValueChange = { phoneNumber.value = it },
                    label = { Text("Phone Number") },
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Phone
                    )
                )

                OutlinedTextField(
                    value = dob.value,
                    onValueChange = {
                        dob.value = it
                        isErrorDob.value = !dobRegex.matches(dob.value)
                    },
                    label = { Text(if (isErrorDob.value) "Invalid DOB (dd/mm/yyyy)" else "Date of Birth") },
                    leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    isError = isErrorDob.value
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            onSave()
                            onDismiss()
                            name.value = ""
                            email.value = ""
                            phoneNumber.value = ""
                            dob.value = ""
                        },
                        enabled = name.value.isNotBlank() && email.value.isNotBlank()
                                && phoneNumber.value.isNotBlank() && dob.value.isNotBlank() && !isError.value && !isErrorDob.value
                    ) {
                        if (!isEditingEnabled.value) {

                            Text("Save")
                        } else {
                            Text("Update")
                        }
                    }
                }
            }
        }
    }
}