package com.example.galleryapp.screens

//package com.example.galleryapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.galleryapp.networkCall.RepoViewModel

@Composable
fun GalleryScreen(viewModel: RepoViewModel,repo:String) {
    viewModel.fetchRepoContents(
        owner = "gongobongofounder",   // Change this
        repo = repo,  // Change this
        path = "" // Change this if necessary
    )

    val repoFiles = viewModel.repoFiles

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(repoFiles) { file ->
            if (file.name.endsWith(".jpg") || file.name.endsWith(".png")) {
                Image(
                    painter = rememberImagePainter(file.download_url),
                    contentDescription = file.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

