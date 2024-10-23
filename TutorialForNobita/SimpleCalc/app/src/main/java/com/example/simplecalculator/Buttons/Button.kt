package com.example.simplecalculator.Buttons

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun Button(
    modifier: Modifier = Modifier,
    text: String? = null,
    onClick: () -> Unit,
    tint: Color = Color.White.copy(alpha = 0.8f),
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    elevation: Dp = 4.dp,
    icon: @Composable() () -> Unit = {}
) {
    var buttonSize by remember { mutableStateOf(69.dp) }  // Initial size of the button
    val smallSize = 65.dp
    val normalSize = 69.dp

    LaunchedEffect(buttonSize) {
        if (buttonSize == smallSize) {
            delay(200)  // Wait for a while after shrinking
            buttonSize = normalSize  // Restore the original size
        }
    }

    // Surface works well for better control of shapes and elevation handling
    Surface(
        modifier = modifier
            .padding(all = 4.dp)
            .clip(CircleShape) // Clip to circular shape
            .clickable {
                onClick.invoke()
                buttonSize = smallSize  // Shrink button when clicked
            }
            .animateContentSize(),  // Smooth animation for size change
        color = backgroundColor,  // Background color
        shape = CircleShape,      // Circular shape
        shadowElevation = elevation  // Elevation for shadow
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(buttonSize)
        ) {
            if (text != null) {
                Text(
                    text = text,
                    color = tint,
                    fontSize = 26.sp,
                    modifier = Modifier.padding(4.dp)
                )
            } else {
                icon()
            }
        }
    }
}

@Preview
@Composable
fun RoundIconButtonPreview() {
    Button(
        onClick = { Log.d("button", "RoundIconButtonPreview: Button Clicked") },
        text = "×"
    )
}
