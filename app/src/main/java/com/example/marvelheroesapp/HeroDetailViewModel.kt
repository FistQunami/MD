package com.example.marvelheroesapp


import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import generateMD5Hash
import kotlinx.coroutines.launch

class HeroDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> get() = _isLoading

    private val _heroState = mutableStateOf<HeroForRender?>(null)
    val heroState: State<HeroForRender?> get() = _heroState

    private val _hasError = mutableStateOf(false)
    val hasError: State<Boolean> get() = _hasError

    private val heroBD: HeroBD = AppDatabase.getDatabase(application).heroBD()

    fun fetchHeroDetails(heroId: Int) {
        _isLoading.value = true
        _hasError.value = false
        viewModelScope.launch {
            try {
                val heroEntity = heroBD.getHeroById(heroId)
                if (heroEntity != null) {
                    _heroState.value = entityHeroToHeroForRender(heroEntity)
                } else {
                    fetchHeroFromNetwork(heroId)
                }
            } catch (e: Exception) {
                _hasError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    private suspend fun fetchHeroFromNetwork(heroId: Int) {
        try {
            val timeStamp = System.currentTimeMillis().toString()
            val response = RetrofitInstance.api.getCharacterById(
                apiKey = ApiKeys.PUBLIC_KEY.key,
                hash = generateMD5Hash(
                    timeStamp
                            + ApiKeys.PRIVATE_KEY.key
                            + ApiKeys.PUBLIC_KEY.key
                ),
                ts = timeStamp,
                characterId = heroId,
            )
            if (response.isSuccessful && response.body() != null) {
                val hero = response.body()?.data?.results?.firstOrNull()
                if (hero != null) {
                    _heroState.value = heroToHeroForRender(hero)
                    heroBD.insertHero(heroToEntityHero(hero))
                }
            } else {
                _hasError.value = true
            }
        } catch (e: Exception) {
            _hasError.value = true
        }
    }
}
