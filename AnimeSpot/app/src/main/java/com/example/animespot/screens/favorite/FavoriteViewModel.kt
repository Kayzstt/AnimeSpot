package com.example.animespot.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.animespot.database.FavoriteAnime
import com.example.animespot.database.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: Repository) : ViewModel() {
    private val _favorites = MutableStateFlow<List<FavoriteAnime>?>(null)
    val favorites: StateFlow<List<FavoriteAnime>?> = _favorites.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun fetchFavorites(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val favoriteListFlow = repository.getFavoritesByUser(userId).asFlow()
            favoriteListFlow.collect { list ->
                _favorites.value = list
                _isLoading.value = false
            }
        }
    }
    fun deleteFavorite(anime: FavoriteAnime) {
        viewModelScope.launch {
            repository.deleteFavorite(anime)
        }
    }

}