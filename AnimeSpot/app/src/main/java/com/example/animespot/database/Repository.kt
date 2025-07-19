package com.example.animespot.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object Repository {
     private var database: AnimeDatabase? = null

    fun initDatabase(context: Context){
        if(database==null){
            database = AnimeDatabase.getInstance(context)
        }

    }
    suspend fun insertFavorite(favoriteAnime: FavoriteAnime) = withContext(Dispatchers.IO) {
        database?.animeDao()?.addFavorite(favoriteAnime)
    }


    suspend fun deleteFavorite(favoriteAnime: FavoriteAnime) = withContext(Dispatchers.IO) {
        database?.animeDao()?.removeFavorite(favoriteAnime)
    }


    fun getFavoritesByUser(userId: String): LiveData<List<FavoriteAnime>> {
        val dao = database?.animeDao()
        return dao?.getFavoriteAnimesByUser(userId) ?: MutableLiveData(emptyList())
    }
    suspend fun isFavorite(userId: String, animeId: String): Boolean {

        return withContext(Dispatchers.IO) {
            database?.animeDao()?.isFavorite(userId, animeId) ?: false
        }
    }
}