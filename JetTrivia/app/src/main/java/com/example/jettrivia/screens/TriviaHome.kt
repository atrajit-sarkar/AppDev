package com.example.jettrivia.screens

import androidx.compose.runtime.Composable
import com.example.jettrivia.component.Questions

@Composable
fun TriviaHome(viewModel: QuestionsViewModel) {
    Questions(viewModel = viewModel)

}