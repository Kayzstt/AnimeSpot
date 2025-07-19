package com.example.animespot.api

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi {
    private const val baseUrl = "https://graphql.anilist.co"


    private val gson = GsonBuilder()
        .registerTypeAdapter(object : TypeToken<List<Media>>() {}.type, MediaAdapter())
        .create()


    private val gsonConverterFactory = GsonConverterFactory.create(gson)


    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(gsonConverterFactory)
        .build()


    val aniListAPI: AniListAPI = retrofit.create(AniListAPI::class.java)
}