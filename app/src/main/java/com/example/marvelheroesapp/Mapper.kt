package com.example.marvelheroesapp

import Hero

data class HeroForRender (
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String,
)

fun entityHeroToHeroForRender(heroEntity: EntityHero): HeroForRender {
    return HeroForRender(
        id = heroEntity.id,
        name = heroEntity.name,
        description = heroEntity.description,
        thumbnail = heroEntity.thumbnail
    )
}

fun heroToHeroForRender(hero: Hero): HeroForRender {
    return HeroForRender(
        id = hero.id,
        name = hero.name,
        description = hero.description,
        thumbnail = hero.thumbnail.fullUrl
    )
}

fun heroToEntityHero(hero: Hero): EntityHero {
    return EntityHero(
        id = hero.id,
        name = hero.name,
        description = hero.description,
        thumbnail = hero.thumbnail.fullUrl
    )
}
