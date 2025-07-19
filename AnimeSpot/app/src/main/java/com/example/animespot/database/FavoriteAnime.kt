package com.example.animespot.database

import androidx.room.Entity


@Entity(tableName = "FavoriteAnime",primaryKeys = ["animeId", "userId"])
data class FavoriteAnime(
    val animeId: String,
    val userId: String,
    val title: String,
    val description: String,
    val imageUrl: String
)