package sreens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.contacts.search


private val mutableState: MutableState<String> = mutableStateOf("")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Contacts_homeScreen(label: String ,
                        textFieldValue: MutableState<String>,
                        focusRequester: FocusRequester,
                        isFocused: MutableState<Boolean>,
                        focusManager: FocusManager,
                        showSearchBar: MutableState<Boolean> = remember { mutableStateOf(false) }
){
    val Add_contact_dialogue = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(

                title = {
                    Text(text = "Contacts")
                },
            )

        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = Color(0xFF005285),
                onClick = {
                    Add_contact_dialogue.value = true
                },

            ) {
                Icon(
                    imageVector = if (!Add_contact_dialogue.value) Icons.Default.Add else Icons.Default.AccountBox,
                    contentDescription = "add",
                    modifier = Modifier.size(40.dp)
                )

            }

        }
    ) {
            innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 12.dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(12.dp),
            horizontalAlignment = Alignment.Start
        ){
            if (!isFocused.value) {
                item {
                    search(
                        label = label,
                        textFieldValue = textFieldValue,
                        focusRequester = focusRequester,
                        isFocused = isFocused,
                        focusManager = focusManager,
                        showSearchBar = showSearchBar
                    )
                }



                }
        }
    }

}

