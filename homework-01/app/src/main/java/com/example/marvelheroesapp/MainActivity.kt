package com.example.marvelheroesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

val heroes = listOf(
    Hero(
        name = "Deadpool", description = "Please don’t make the super suit green...or animated!",
        imageUrl = "https://iili.io/JMnAfIV.png"
    ),
    Hero(
        name = "Iron Man",
        description = "I AM IRON MAN",
        imageUrl = "https://iili.io/JMnuDI2.png"
    ),
    Hero(
        name = "Spider Man",
        description = "In iron suit",
        imageUrl = "https://iili.io/JMnuyB9.png"
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelApp(
                heroes = heroes
            )
        }
    }
}