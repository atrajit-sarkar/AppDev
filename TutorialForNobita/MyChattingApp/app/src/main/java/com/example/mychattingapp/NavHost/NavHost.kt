package com.example.mychattingapp.NavHost

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mychattingapp.LocaldbLogics.ViewModel.ChatAppViewModel
import com.example.mychattingapp.Screens.ChatScreen
import com.example.mychattingapp.Screens.Contacts
import com.example.mychattingapp.Screens.HomeScreen
import com.example.mychattingapp.Screens.SettingsScreen
import com.example.mychattingapp.Screens.SwipeableScreensWithTabs

const val durationMillis = 320 // Slightly increased duration for smoother transitions

@Composable
fun Navigation(viewModel: ChatAppViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home_screen",
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(
                    durationMillis = durationMillis,
                    easing = FastOutSlowInEasing
                )
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(
                    durationMillis = durationMillis,
                    easing = FastOutSlowInEasing
                )
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(
                    durationMillis = durationMillis,
                    easing = FastOutSlowInEasing
                )
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(
                    durationMillis = durationMillis,
                    easing = FastOutSlowInEasing
                )
            )
        }
    ) {
//        composable("home_screen") { HomeScreen(navController = navController, viewModel = viewModel) }
        composable("home_screen") {
            SwipeableScreensWithTabs(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable("chat_screen/{chatId}") {
            it.arguments?.getString("chatId")
                ?.let { it1 ->
                    ChatScreen(
                        navController = navController,
                        chatId = it1.toInt(),
                        viewModel = viewModel
                    )
                }
        }

        composable(
            route = "chat_screen/{chatId}/{messageId}",
            arguments = listOf(
                navArgument("chatId") { type = NavType.IntType },
                navArgument("messageId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getInt("chatId") ?: 0
            val messageId = backStackEntry.arguments?.getInt("messageId") ?: 0

            ChatScreen(
                chatId = chatId,
                viewModel = viewModel,
                navController = navController,
                messageId = messageId
            )
        }
        composable("settings_screen") {
            SettingsScreen(navController = navController)
        }
        composable("allcontact_screen") {
            Contacts(navController = navController)
        }

    }
}
