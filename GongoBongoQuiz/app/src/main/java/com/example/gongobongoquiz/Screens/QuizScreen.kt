package com.example.gongobongoquiz.Screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gongobongoquiz.viewModel.MainViewModel

@Composable
fun QuizScreen(viewModel: MainViewModel) {
    val questions = viewModel.quizQuestions.collectAsState().value

    LazyColumn {
        items(questions) { question ->
            Text(question.question)
            question.options.forEach { option ->
                Text(option, modifier = Modifier.padding(8.dp))
            }
        }
    }
}
