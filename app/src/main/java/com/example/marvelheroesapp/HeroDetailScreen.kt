package com.example.marvelheroesapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun HeroDetailScreen(heroId: Int, onBackClick: () -> Unit) {
    val viewModel: HeroDetailViewModel = viewModel()

    val hero = viewModel.heroState.value
    val isLoading = viewModel.isLoading.value
    val hasError = viewModel.hasError.value

    LaunchedEffect(heroId) {
        viewModel.fetchHeroDetails(heroId)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_main_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            hasError -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    FallbackScreen (
                        onRetry = { viewModel.fetchHeroDetails(heroId) }
                    )
                }
            }

            hero != null -> {
                Image(
                    painter = rememberAsyncImagePainter(model = hero.thumbnail),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )

                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 24.dp, bottom = 32.dp)
                        .align(Alignment.BottomStart)
                ) {
                    Text(
                        text = hero.name, style = TextStyle(
                            fontSize = 34.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                    if (hero.description.isNotBlank()) {
                        Spacer(modifier = Modifier.size(15.dp))

                        Text(
                            text = hero.description, style = TextStyle(
                                fontSize = 22.sp,
                                color = Color.White.copy(alpha = 0.9f),
                                lineHeight = 30.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }
                }
            }
        }

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
            )
        }
    }
}