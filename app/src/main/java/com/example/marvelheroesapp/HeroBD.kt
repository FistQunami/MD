package com.example.marvelheroesapp

import android.content.Context
import androidx.room.*

// ------------------------- DAO -------------------------
@Dao
interface HeroBD { // Название оставлено прежним, чтобы не путать
    @Query("SELECT * FROM heroes")
    suspend fun getAllHeroes(): List<EntityHero>

    @Query("SELECT * FROM heroes WHERE id = :heroId LIMIT 1")
    suspend fun getHeroById(heroId: Int): EntityHero?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeroes(heroes: List<EntityHero>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHero(hero: EntityHero)

    @Query("DELETE FROM heroes")
    suspend fun deleteAllHeroes()
}

// ------------------------- База данных -------------------------
@Database(entities = [EntityHero::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun heroBD(): HeroBD // Название метода соответствует интерфейсу

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Проверяем, что контекст передан корректно
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, // Правильный контекст
                    AppDatabase::class.java,
                    "app_database" // Имя базы данных
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

// ------------------------- Сущность -------------------------
@Entity(tableName = "heroes")
data class EntityHero(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String
)
