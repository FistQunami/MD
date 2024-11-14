package com.example.marvelheroesapp

import Hero
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HeroDetailViewModel : ViewModel() {
    private val _heroState = mutableStateOf<Hero?>(null)
    val heroState: State<Hero?> get() = _heroState

    private val _hasError = mutableStateOf(false)
    val hasError: State<Boolean> get() = _hasError



    fun fetchHeroDetails(heroId: Int) {
        _hasError.value = false
        viewModelScope.launch {
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
                    _heroState.value = response.body()?.data?.results?.firstOrNull()
                } else {
                    _hasError.value = true
                }
            } catch (e: Exception) {
                _hasError.value = true
            }
        }
    }
}
