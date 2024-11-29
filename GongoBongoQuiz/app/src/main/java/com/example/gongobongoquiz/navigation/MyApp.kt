package com.example.gongobongoquiz.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gongobongoquiz.Screens.FolderScreen
import com.example.gongobongoquiz.Screens.QuizScreen
import com.example.gongobongoquiz.viewModel.MainViewModel

@Composable
fun MyApp(viewModel: MainViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "folders") {
        composable("folders") { FolderScreen(navController, viewModel) }
        composable("quiz") { QuizScreen(viewModel) }
    }
}
