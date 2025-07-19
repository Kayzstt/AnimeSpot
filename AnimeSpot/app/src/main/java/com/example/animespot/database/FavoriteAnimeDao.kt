package com.example.animespot.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteAnimeDao {
    @Query("SELECT * FROM FavoriteAnime WHERE userId = :userId")
    fun getFavoriteAnimesByUser(userId: String): LiveData<List<FavoriteAnime>>

    @Insert
    fun addFavorite(favoriteAnime: FavoriteAnime)

    @Delete
    fun removeFavorite(favoriteAnime: FavoriteAnime)
    @Query("SELECT COUNT(1) > 0 FROM FavoriteAnime WHERE userId = :userId AND animeId = :animeId")
    suspend fun isFavorite(userId: String, animeId: String): Boolean
}