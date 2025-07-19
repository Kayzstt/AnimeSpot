package com.example.animespot.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

data class AniListResponse(
    val data: Data
)

data class Data(
    val Page: Page?,

)

data class Page(
    @SerializedName("airingSchedules")
    var airingSchedules: List<AiringSchedules>?,
    var media: List<Media>
)

data class AiringSchedules(
    @JsonAdapter(MediaAdapter::class)
    val media: List<Media>
)

data class Media(
    val id: Int,
    val title: Title?,
    val description: String?,
    val coverImage: CoverImage?,
    val trailer: Trailer?,
    val averageScore : Int?,
    val episodes: Int?,
    val nextAiringEpisode: nextAiringEpisode?,
    val status: String?,
    val seasonYear: Int?,
    val genres : List<String>?
)

data class Title(
    val  userPreferred: String?
)

data class CoverImage(
    val large: String?,
    var extraLarge:String?
)

data class Trailer(
    val id: String?,
    val site: String?
)

data class nextAiringEpisode(
    val id: String?,
    val airingAt:Int?,
    val episode:Int?,
    val timeUntilAiring: Int?


)
class MediaAdapter : JsonDeserializer<List<Media>> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): List<Media> {
        return if (json.isJsonObject) {
            listOf(context.deserialize(json, Media::class.java))
        } else if (json.isJsonArray) {
            context.deserialize(json, typeOfT)
        } else {
            emptyList()
        }
    }
}
