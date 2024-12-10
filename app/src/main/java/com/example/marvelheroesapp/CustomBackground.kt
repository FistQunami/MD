package com.example.marvelheroesapp

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill

@Composable
fun CustomBackground() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawRect(
            color = Color.Black,
            size = size
        )

        val path = Path().apply {
            moveTo(size.width, size.height)
            lineTo(size.width * 0.5f, size.height)
            lineTo(size.width, size.height * 0.5f)
            close()
        }

        drawPath(
            path = path,
            color = Color.Red,
            style = Fill
        )
    }
}
