package com.example.marvelheroesapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

@Composable
fun HeroListScreen(
    heroes: List<Hero>,
    onHeroClick: (Hero) -> Unit
) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.ic_main_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .padding(top = 30.dp, bottom = 30.dp)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = "https://iili.io/JMnuvbp.png"),
                contentDescription = "Marvel Logo",
            )

            Spacer(modifier = Modifier.size(25.dp))

            Text(
                text = "Choose your hero",
                style = TextStyle(
                    fontSize = 28.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            )

            Spacer(modifier = Modifier.size(25.dp))

            val lazyListState = rememberLazyListState()
            val snapFlingBehavior = rememberSnapFlingBehavior(
                lazyListState = lazyListState,
                snapPosition = SnapPosition.Center
            )

            LazyRow(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState,
                flingBehavior = snapFlingBehavior,
                contentPadding = PaddingValues(horizontal = 30.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(heroes) { hero ->
                    HeroPagerItem(hero = hero, onClick = { onHeroClick(hero) })
                }
            }
        }
    }
}

@Composable
fun HeroPagerItem(hero: Hero, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(350.dp)
            .fillMaxHeight()
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = hero.imageUrl),
            contentDescription = hero.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.9f)
                .clip(RoundedCornerShape(5.dp))
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
                .padding(start = 28.dp, bottom = 60.dp)
        )
    }
}
