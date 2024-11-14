package com.example.marvelheroesapp

import Hero
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter


@Composable
fun HeroCard(hero: Hero, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(350.dp)
            .fillMaxHeight()
            .clickable(onClick = onClick)
    ) {
        val painter = rememberAsyncImagePainter(model = hero.thumbnail.fullUrl)

        Image(
            painter = painter,
            contentDescription = hero.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.9f)
                .clip(RoundedCornerShape(10.dp))
        )

        Text(
            text = hero.name,
            style = TextStyle(
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 28.dp, bottom = 60.dp, end = 28.dp)
        )
    }
}
