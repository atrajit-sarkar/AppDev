package com.example.mycalculator.Navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycalculator.Data.HomeScreenData.viewModel.ContactViewModel
import com.example.mycalculator.Screens.ContactSelectionScreen
import com.example.mycalculator.Screens.GroupContactCreationScreen
import com.example.mycalculator.Screens.GroupScreen
import com.example.mycalculator.Screens.HomeScreen
import com.example.mycalculator.Screens.NextLayerContactSelectionScreen

const val durationMillis = 320 // Slightly increased duration for smoother transitions
@Composable
fun Navigation(
    viewModel: ContactViewModel
) {
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
        composable("home_screen") {
            HomeScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable("group_creation_screen") {
            GroupContactCreationScreen(
                navController = navController,
                viewModel
            )
        }
        composable("contact_selection_screen/{textFieldValue}") {
            ContactSelectionScreen(
                navController = navController,
                viewModel = viewModel,
                textFieldValue = it.arguments?.getString("textFieldValue").toString()
            )
        }

        composable("group_screen/{groupId}") {
            GroupScreen(
                navController = navController,
                viewModel = viewModel,
                groupId = it.arguments?.getString("groupId").toString()
            )
        }
        composable("next_layer_contact_selection_screen/{groupId}") {
            NextLayerContactSelectionScreen(
                navController = navController,
                viewModel = viewModel,
                groupId = it.arguments?.getString("groupId").toString()
            )
        }
    }
}
