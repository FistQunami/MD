package com.example.marvelheroesapp

import Hero
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val heroesState = mutableStateOf(emptyList<Hero>())
    private val hasError = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchHeroes()

        setContent {
            MarvelApp(
                heroes = heroesState.value,
                hasError = hasError.value,
                onRetry = { fetchHeroes() }
            )
        }
    }

    private fun fetchHeroes() {
        hasError.value = false
        lifecycleScope.launch {
            try {
                val timeStamp = System.currentTimeMillis().toString()
                val response = RetrofitInstance.api.getCharacters(
                    apiKey = ApiKeys.PUBLIC_KEY.key,
                    hash = generateMD5Hash(
                        timeStamp
                                + ApiKeys.PRIVATE_KEY.key
                                + ApiKeys.PUBLIC_KEY.key
                    ),
                    ts = timeStamp
                )
                if (response.isSuccessful && response.body() != null) {
                    heroesState.value = response.body()?.data?.results ?: emptyList()
                } else {
                    hasError.value = true
                }
            } catch (e: Exception) {
                hasError.value = true
            }
        }
    }
}

