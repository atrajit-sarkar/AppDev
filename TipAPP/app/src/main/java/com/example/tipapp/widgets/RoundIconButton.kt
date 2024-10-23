package com.example.tipapp.widgets

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

var onclickSize =40.dp
val IconbuttonSizeModifier = Modifier.size(onclickSize)


@Composable
fun RoundIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    tint: Color = Color.Red.copy(alpha = 0.8f),
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    elevation: Dp = 4.dp
) {
    Card(
        modifier = modifier
            .padding(all = 4.dp)
            .clickable {
                onClick.invoke()
                onclickSize=55.dp
            }
            .then(IconbuttonSizeModifier),
        shape = CircleShape,
        colors = CardDefaults.cardColors(backgroundColor),
        elevation = CardDefaults.cardElevation(elevation)
    ) {
        Box(
            contentAlignment = Alignment.Center, // Centers content inside the Box
            modifier = modifier.size(onclickSize) // Match the Card size
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = "Plus or Minus Icon",
                tint = tint
            )
        }
    }
}
