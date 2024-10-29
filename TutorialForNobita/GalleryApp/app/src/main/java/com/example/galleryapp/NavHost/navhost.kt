package com.example.galleryapp.NavHost

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.galleryapp.networkCall.RepoViewModel
import com.example.galleryapp.screens.GalleryScreen
import com.example.galleryapp.screens.HomeScreen
import com.example.galleryapp.screens.TestScreen

//import com.example.morrorlessrawgallery.Screens.HomeScreen

@Composable
fun Navigation(viewModel: RepoViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home_screen") {
        composable("home_screen") { HomeScreen(navController) }
        composable("galleryScreen/{repoUrl}") { backStackEntry ->
            backStackEntry.arguments?.getString("repoUrl")?.let {
                GalleryScreen(viewModel = viewModel, repo = it)
            }
        }
        composable("testscreen/{test}") { test ->
            test.arguments?.getString("test")?.let { TestScreen(test = it) }


        }
    }
}
