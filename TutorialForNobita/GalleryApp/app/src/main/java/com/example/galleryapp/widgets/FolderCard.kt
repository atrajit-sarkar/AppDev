package com.example.morrorlessrawgallery.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil3.compose.rememberAsyncImagePainter

@Composable
fun Folders(navController: NavController,folderImage:String,folderName:String,folderDescription:String,onClick:()->Unit={}) {

    val cardElevation = remember { Animatable(4f) } // For scaling animation
    val isPressed = remember { mutableStateOf(false) }

    // Animated visibility and load for image
    val imagePainter = rememberImagePainter(
        data = folderImage,

    )

    Card(
        modifier = Modifier
            .padding(12.dp)
            .wrapContentHeight()
            .width(156.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        cardElevation.animateTo(cardElevation.value) // Scale effect on press
                        tryAwaitRelease()
                        cardElevation.animateTo(cardElevation.value) // Return to normal
                    }
                )
            }
            .graphicsLayer { scaleX = 1.02f; scaleY = 1.02f }
            .clickable {
                onClick()
            },
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSurface)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
        ) {
            AnimatedVisibility(visible = true) {
                Image(
                    painter = imagePainter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)) // Subtle background if image fails
                        .animateEnterExit(
                            enter = fadeIn(
                                initialAlpha = 0f,
                                animationSpec = tween(durationMillis = 500)
                            ),
                            exit = fadeOut(animationSpec = tween(durationMillis = 300))
                        )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = folderName,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary,
//                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = folderDescription,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
//                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
