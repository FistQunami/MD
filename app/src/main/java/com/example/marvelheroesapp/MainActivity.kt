package com.example.marvelheroesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import generateMD5Hash



class MainActivity : ComponentActivity() {

    private val heroesState = mutableStateOf(emptyList<HeroForRender>())
    private val hasError = mutableStateOf(false)

    private lateinit var database: AppDatabase
    private lateinit var heroBD: HeroBD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = AppDatabase.getDatabase(this)
        heroBD = database.heroBD()

        loadFromDb()

        setContent {
            MarvelApp(
                heroes = heroesState.value,
                hasError = hasError.value,
                onRetry = { loadFromDb() }
            )
        }
    }

    private fun loadFromDb() {
        hasError.value = false
        lifecycleScope.launch {
            val heroesFromDb = heroBD.getAllHeroes()
            if (heroesFromDb.isNotEmpty()) {
                heroesState.value = heroesFromDb.map { entityHeroToHeroForRender(it) }

                fetchHeroesAndUpdateDb()
            } else {
                fetchHeroesAndUpdateDb()

                if (heroesState.value.isEmpty()) {
                    hasError.value = true
                }
            }
        }
    }

    private suspend fun fetchHeroesAndUpdateDb() {
        lifecycleScope.launch {
            try {
                val timeStamp = System.currentTimeMillis().toString()
                val response = RetrofitInstance.api.getCharacters(
                    apiKey = ApiKeys.PUBLIC_KEY.key,
                    hash = generateMD5Hash(
                        timeStamp + ApiKeys.PRIVATE_KEY.key + ApiKeys.PUBLIC_KEY.key
                    ),
                    ts = timeStamp
                )
                if (response.isSuccessful && response.body() != null) {
                    val heroes = response.body()?.data?.results ?: emptyList()

                    val heroEntities = heroes.map { heroToEntityHero(it) }
                    val uiHeroes = heroes.map { heroToHeroForRender(it) }

                    heroBD.deleteAllHeroes()
                    heroBD.insertHeroes(heroEntities)

                    if (heroesState.value.isEmpty()) {
                        heroesState.value = uiHeroes
                    }
                } else {
                    if (heroesState.value.isEmpty()) {
                        hasError.value = true
                    }
                }
            } catch (e: Exception) {
                if (heroesState.value.isEmpty()) {
                    hasError.value = true
                }
            }
        }
    }
}
