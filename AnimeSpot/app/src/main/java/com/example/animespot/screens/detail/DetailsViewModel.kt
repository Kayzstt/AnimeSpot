package com.example.animespot.screens.detail


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animespot.api.Media
import com.example.animespot.database.FavoriteAnime
import com.example.animespot.database.Repository
import com.example.animespot.repository.UserRepository
import kotlinx.coroutines.launch


class DetailsViewModel(private val repository: Repository,userRepository: UserRepository) : ViewModel() {
    private val _selectedMedia = MutableLiveData<Media?>(null)
    val selectedMedia: LiveData<Media?> = _selectedMedia

    var user = userRepository

    private val _isFavorite = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> = _isFavorite


    private val youtubeBaseUrl = "https://www.youtube.com/watch?v="
    private val _fullTrailerUrl = MutableLiveData<String?>()
    val fullTrailerUrl: LiveData<String?> = _fullTrailerUrl

    fun selectMedia(media: Media) {
        _selectedMedia.value = media
        _fullTrailerUrl.value = youtubeBaseUrl + (media.trailer?.id ?: "")
    }


    fun checkIfFavorite(animeId: String) {
        viewModelScope.launch {
            val result = repository.isFavorite(user.getCurrentUser()?.uid.toString(), animeId)
            _isFavorite.postValue(result)
        }
    }
    fun addToFavorites(userRepository: UserRepository) {
        val media = _selectedMedia.value
        media?.let {
            viewModelScope.launch {
                    repository.insertFavorite(
                        FavoriteAnime(
                            animeId = it.id.toString(),
                            userId = userRepository.getCurrentUser()?.uid.toString(),
                            title = it.title?.userPreferred ?: "Unknown",
                            description = it.description ?: "No description",
                            imageUrl = it.coverImage?.extraLarge ?: ""
                        )
                    )
                 _isFavorite.postValue(true)
                }
            }
        }
    }
