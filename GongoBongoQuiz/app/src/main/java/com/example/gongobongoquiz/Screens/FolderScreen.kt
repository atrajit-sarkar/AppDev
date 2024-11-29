package com.example.gongobongoquiz.Screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.gongobongoquiz.ui.theme.GongoBongoQuizTheme
import com.example.gongobongoquiz.viewModel.MainViewModel

@Composable
fun FolderScreen(navController: NavController, viewModel: MainViewModel) {
    val contents = viewModel.contents.collectAsState().value
    GongoBongoQuizTheme {
        Log.d("github", "FolderScreen: $contents")


        LazyColumn {
            items(contents) { content ->
                Row(modifier = Modifier
                    .clickable {
                        if (content.type == "dir") {
                            viewModel.fetchContents(
                                "atrajit-sarkar",
                                "Quiz-Development",
                                content.path
                            )
                        } else if (content.type == "file") {
                            viewModel.loadQuiz("https://raw.githubusercontent.com/your-username/your-repo/main/${content.path}")
                            navController.navigate("quiz")
                        }
                    }
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    Log.d("github", "FolderScreen: ${content.name}")
                    Text(
                        "Below is the Folders............",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(content.name)

                }


            }
        }
    }
}
