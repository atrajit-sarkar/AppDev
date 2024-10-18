package com.example.instasexies

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView

@Composable
fun VideoPlayer(url: String) {
    val context = LocalContext.current
    val exoPlayer = rememberExoPlayer(context, url)

    AndroidView(
        factory = { PlayerView(context).apply { player = exoPlayer } },
        modifier = Modifier
            .height(1050.dp)
            .fillMaxWidth()
    )
}

@Composable
fun rememberExoPlayer(context: Context, url: String): ExoPlayer {

    return remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(url))
            prepare()
            playWhenReady = false
        }
    }
}
