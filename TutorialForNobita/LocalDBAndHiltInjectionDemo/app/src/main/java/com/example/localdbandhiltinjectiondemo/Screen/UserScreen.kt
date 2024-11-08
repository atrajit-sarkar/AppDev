package com.example.localdbandhiltinjectiondemo.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.localdbandhiltinjectiondemo.ViewModel.UserViewModel
import com.example.localdbandhiltinjectiondemo.dao.User


@Composable
fun UserScreen(viewModel: UserViewModel,paddingValues: PaddingValues) {
    val users by viewModel.allUsers.observeAsState(emptyList())

    Column (modifier = Modifier.padding(paddingValues)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally){
        // Display list of users
        LazyColumn (modifier = Modifier.height(500.dp)){
            items(users) { user ->
                Text(text = "${user.name}, ${user.age}")
            }
        }
        // Add user button
        Button(onClick = {
            viewModel.addUser(User(name = "New User", age = 25))
        }) {
            Text("Add User")
        }
    }
}
