package com.example.mychattingapp.NavHost

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mychattingapp.LocaldbLogics.ViewModel.ChatAppViewModel
import com.example.mychattingapp.Screens.ChatScreen
import com.example.mychattingapp.Screens.HomeScreen

@Composable
fun Navigation(viewModel: ChatAppViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home_screen",
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { 1000 }, // Slide in from the right
                animationSpec = tween(69)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -1000 }, // Slide out to the left
                animationSpec = tween(69)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -1000 }, // Slide in from the left when popping back
                animationSpec = tween(69)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { 1000 }, // Slide out to the right when popping back
                animationSpec = tween(69)
            )
        }
    ) {
        composable("home_screen") { HomeScreen(navController = navController, viewModel = viewModel) }
        composable("chat_screen/{chatId}") {
            it.arguments?.getString("chatId")
                ?.let { it1 -> ChatScreen(navController = navController, chatId = it1.toInt(), viewModel = viewModel) }
        }
    }
}
