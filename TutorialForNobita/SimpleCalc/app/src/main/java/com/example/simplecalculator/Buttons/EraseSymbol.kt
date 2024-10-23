package com.example.simplecalculator.Buttons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EraseSymbolIcon(
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    strokeWidth: Float = 5f // Thickness of the border
) {
    Canvas(
        modifier = modifier.size(80.dp) // Icon size
    ) {
        // Draw arrow-like shape around the cross
        drawPath(
            path = androidx.compose.ui.graphics.Path().apply {
                moveTo(size.width * 0.1f, size.height * 0.5f)
                lineTo(size.width * 0.4f, size.height * 0.2f)
                lineTo(size.width * 0.9f, size.height * 0.2f)
                lineTo(size.width * 0.9f, size.height * 0.8f)
                lineTo(size.width * 0.4f, size.height * 0.8f)
                close()
            },
            color = color,
            style = Stroke(strokeWidth)
        )

        // Draw the cross (X) symbol
        drawLine(
            color = color,
            start = androidx.compose.ui.geometry.Offset(size.width * 0.55f, size.height * 0.35f),
            end = androidx.compose.ui.geometry.Offset(size.width * 0.75f, size.height * 0.55f),
            strokeWidth = strokeWidth
        )

        drawLine(
            color = color,
            start = androidx.compose.ui.geometry.Offset(size.width * 0.75f, size.height * 0.35f),
            end = androidx.compose.ui.geometry.Offset(size.width * 0.55f, size.height * 0.55f),
            strokeWidth = strokeWidth
        )
    }
}

@Preview
@Composable
fun PreviewCustomArrowCrossIcon() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        EraseSymbolIcon(color = Color.White)
    }
}
