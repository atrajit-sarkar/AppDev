package com.example.instasexies

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun RepoGalleryScreen(viewModel: RepoViewModel) {
    val repoFiles = viewModel.repoFiles

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(6.dp, vertical = 0.dp)
    ) {
        items(repoFiles) { file ->
            if (file.name.endsWith(".jpg") || file.name.endsWith(".png")) {
                Image(
                    painter = rememberImagePainter(file.download_url),
                    contentDescription = file.name,
                    modifier = Modifier
                        .height(1000.dp)
                        .width(500.dp)
                )
            } else if (file.name.endsWith(".mp4")) {
                VideoPlayer(url = file.download_url ?: "")
            }

            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}
