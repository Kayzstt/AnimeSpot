package com.example.animespot.screens.search

import androidx.lifecycle.ViewModel
import com.example.animespot.api.Media
import com.example.animespot.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel(private val repository: AnimeRepository): ViewModel() {

    private val _mediaList = MutableStateFlow<List<Media>?>(null)
    val mediaList: StateFlow<List<Media>?> = _mediaList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun searchAnime(nameAnime: String) {
        _isLoading.value = true
        repository.getAnime({ mediaList ->
            _mediaList.value = mediaList
            _isLoading.value = false
        }, nameAnime)
    }
}