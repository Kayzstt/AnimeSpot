package com.example.animespot.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface AniListAPI {
        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("/")
        fun getAnimes(@Body request: JsonObject): Call<AniListResponse>

        @Headers("Content-Type: application/json", "Accept: application/json")
        @POST("/")
        fun searchAnimes(@Body request: JsonObject): Call<AniListResponse>

}



