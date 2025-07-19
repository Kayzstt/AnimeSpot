package com.example.animespot.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animespot.api.Media
import com.example.animespot.repository.AnimeRepository
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel(private val repository: AnimeRepository) : ViewModel() {

    private val _animes = MutableLiveData<List<Media>>()
    val animes: LiveData<List<Media>> = _animes
    init {
        fetchAnimesForWeek()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchAnimesForWeek() {
        viewModelScope.launch {
            repository.getAnimesForWeek { animes ->
                animes?.let {
                    _animes.postValue(it)
                }
            }
        }
    }
}

