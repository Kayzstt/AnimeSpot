package com.example.animespot.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.animespot.api.*
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class AnimeRepository(private val api: AniListAPI) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAnimesForWeek(onResult: (List<Media>?) -> Unit) {
        val calendar = Calendar.getInstance()
        val startDate = (calendar.timeInMillis / 1000).toInt()
        calendar.add(Calendar.DAY_OF_YEAR, 7)
        val endDate = (calendar.timeInMillis / 1000).toInt()
        val page = 1
        val perPage = 50
        val query = """
        query(${'$'}page:Int, ${'$'}perPage:Int,${'$'}startDate:Int,${'$'}endDate:Int){
            Page(page:${'$'}page, perPage:${'$'}perPage){
              airingSchedules(airingAt_greater:${'$'}startDate, airingAt_lesser:${'$'}endDate){
                    media{
                        id
                        title {
                            userPreferred
                        }
                        averageScore
   						nextAiringEpisode {
   						  id
                          airingAt
                          episode
                          timeUntilAiring
   						}

                        description
                        coverImage {
                            large
                            extraLarge
                        }
                        trailer {
                            id
                        }
                    }
                }
            }
           }
    """

        val variables = """{
                "page": $page,
                "perPage": $perPage,
                "startDate": $startDate,
                "endDate" : $endDate
            }"""

        val paramObject = JsonObject()
        paramObject.addProperty("query", query)
        paramObject.addProperty("variables", variables)

        api.getAnimes(paramObject).enqueue(object : Callback<AniListResponse> {
            override fun onResponse(
                call: Call<AniListResponse>,
                response: Response<AniListResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val mediaList =
                        response.body()?.data?.Page?.airingSchedules?.flatMap { it.media }
                    if (!mediaList.isNullOrEmpty()) {
                        onResult(mediaList)
                        Log.i("API Success", "Number of media received: ${mediaList.size}")
                    } else {
                        Log.i("API Success", "Received empty media list")
                        onResult(emptyList())
                    }
                } else {
                    Log.e(
                        "API Error",
                        "Response unsuccessful or empty: ${response.errorBody()?.string()}"
                    )
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<AniListResponse>, t: Throwable) {
                Log.e("API Failure", "Failed to execute request: ${t.message}")
                onResult(null)
            }
        })
    }

    fun getAnime(onResult: (List<Media>) -> Unit, nameAnime: String) {
        val page = 1
        val perPage = 1
        val query = """
           query(${'$'}page:Int,${'$'}perPage:Int,${'$'}nameAnime:String) {
            Page(page:${'$'}page, perPage:${'$'}perPage){
              media(search: ${'$'}nameAnime){
              id
              title {
                userPreferred
              }
              genres
              episodes
       		  averageScore
              description
              status
  			  seasonYear
              coverImage {
                large
                extraLarge
              }
              trailer {
                id
              }
            }
          }
         }
        """

        val variables = JsonObject().apply {
            addProperty("nameAnime", nameAnime)
            addProperty("page", page)
            addProperty("perPage", perPage)
        }

        val paramObject = JsonObject().apply {
            addProperty("query", query)
            add("variables", variables)
        }
        api.searchAnimes(paramObject).enqueue(object : Callback<AniListResponse> {
            override fun onResponse(call: Call<AniListResponse>, response: Response<AniListResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val mediaList = response.body()?.data?.Page?.media // Utilisez emptyList si media est null
                    if (mediaList != null) {
                        onResult(mediaList)
                    }
                    if (mediaList != null) {
                        Log.i("API Success", "Media fetched successfully: ${mediaList.size}")
                    } else {
                        Log.i("API Success", "Received empty media list")
                    }
                } else {
                    Log.e(
                        "API Error",
                        "Response unsuccessful or empty: ${response.errorBody()?.string()}"
                    )
                }
            }

            override fun onFailure(call: Call<AniListResponse>, t: Throwable) {
                Log.e("API Failure", "Failed to execute request: ${t.message}")

            }
        })
    }
}


