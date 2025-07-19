package com.example.animespot.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteAnime::class], version = 1)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun animeDao(): FavoriteAnimeDao


    companion object {
        private const val DATABASE_NAME = "anime_db"
        @Volatile
        private var sInstance: AnimeDatabase? = null

        fun getInstance(context: Context): AnimeDatabase {
            return sInstance ?: synchronized(this) {
                sInstance ?: buildDatabase(context).also { sInstance = it }
            }
        }

        private fun buildDatabase(context: Context): AnimeDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AnimeDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}
