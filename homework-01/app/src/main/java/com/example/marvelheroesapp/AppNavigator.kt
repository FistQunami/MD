package com.example.marvelheroesapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MarvelApp(heroes: List<Hero>) {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "hero_list") {
            composable("hero_list") {
                HeroListScreen(
                    heroes = heroes,
                    onHeroClick = { hero ->
                        navController.navigate("hero_detail/${hero.name}")
                    }
                )
            }
            composable("hero_detail/{heroName}") { backStackEntry ->
                val heroName = backStackEntry.arguments?.getString("heroName")
                val hero = heroes.firstOrNull { it.name == heroName }
                if (hero != null) {
                    HeroDetailScreen(hero = hero, onBackClick = { navController.popBackStack() })
                }
            }
        }
    }
}
