package com.example.galleryapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.morrorlessrawgallery.widgets.Folders

//import com.example.morrorlessrawgallery.widgets.Folders

@Composable
fun HomeScreen(navController: NavController) {
    val itemId = "123"
    LazyColumn {
        item { // Wrap the Row in 'item' scope
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Folders(
                    folderImage = "https://github.com/Somu6969/Dashami-Scooty/raw/refs/heads/main/DSC05968.ARW.jpg",
                    folderName = "Dashami And Scooty",
                    folderDescription = "Roaming at Navami",
                    navController = navController,
                    onClick = {
                        navController.navigate("galleryScreen/Stree2")
                    }
                )
                Folders(
                    folderImage = "",
                    folderName = "Gym",
                    folderDescription = "Making body at AzadHind Club",
                    navController = navController,
                    onClick = {
                        navController.navigate("galleryScreen/Testing")
                    }
                )

            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Folders(
                    folderImage = "https://github.com/Somu6969/Dashami-Scooty/raw/refs/heads/main/DSC05968.ARW.jpg",
                    folderName = "Dashami And Scooty",
                    folderDescription = "Roaming at Navami",
                    navController = navController,
//                    repoUrl = ""
                )
                Folders(
                    folderImage = "",
                    folderName = "Gym",
                    folderDescription = "Making body at AzadHind Club",
                    navController = navController,
//                    repoUrl = ""
                )

            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Folders(
                    folderImage = "https://github.com/Somu6969/Dashami-Scooty/raw/refs/heads/main/DSC05968.ARW.jpg",
                    folderName = "Dashami And Scooty",
                    folderDescription = "Roaming at Navami",
                    navController = navController,
//                    repoUrl = ""
                )
                Folders(
                    folderImage = "",
                    folderName = "Gym",
                    folderDescription = "Making body at AzadHind Club",
                    navController = navController,
//                    repoUrl = ""
                )

            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Folders(
                    folderImage = "https://github.com/Somu6969/Dashami-Scooty/raw/refs/heads/main/DSC05968.ARW.jpg",
                    folderName = "Dashami And Scooty",
                    folderDescription = "Roaming at Navami",
                    navController = navController,
//                    repoUrl = ""
                )
                Folders(
                    folderImage = "",
                    folderName = "Gym",
                    folderDescription = "Making body at AzadHind Club",
                    navController = navController,
//                    repoUrl = ""
                )

            }
        }
    }
}
