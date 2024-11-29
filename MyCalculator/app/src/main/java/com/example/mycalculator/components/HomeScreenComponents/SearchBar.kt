package com.example.mycalculator.components.HomeScreenComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Search Bar function...............
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    label: String = "Search to find desired item",
    textFieldValue: MutableState<String>,
    focusRequester: FocusRequester,
    isFocused: MutableState<Boolean>,
    focusManager: FocusManager,
    showSearchBar: MutableState<Boolean> = remember { mutableStateOf(false) }
) {
    TextField(
        value = textFieldValue.value,
        onValueChange = {
            textFieldValue.value = it
        },
        placeholder = {
            Text(text = "Search")
        },
        label = {
            Text(label)
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged {
                // Storing the active state of the searchbar in the variable isFocused.....
                isFocused.value = it.isFocused
            },
        leadingIcon = {
            if (isFocused.value) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "search",
                    modifier = Modifier.clickable {
                        textFieldValue.value = ""
                        focusManager.clearFocus()
                        showSearchBar.value=false

                    }
                )
            }
        },
        trailingIcon = {
            if (isFocused.value) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "search",
                    modifier = Modifier.clickable {
                        textFieldValue.value = ""
                    })
            }
        },
        supportingText = {
            if (isFocused.value) {
                if (textFieldValue.value == "") {

                    Text(text = "Search for name, phone number, email or dob")
                } else {
                    Text(text = "Matched contacts for \"${textFieldValue.value}\"")
                }
            }
        },
        shape = RoundedCornerShape(50.dp),
    )

}