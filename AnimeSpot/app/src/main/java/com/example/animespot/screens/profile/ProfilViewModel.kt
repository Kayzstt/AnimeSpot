package com.example.animespot.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animespot.R

class ProfileViewModel : ViewModel() {

    private val _selectedImage = MutableLiveData(R.drawable.avatar6)
    val selectedImage: LiveData<Int> = _selectedImage

    fun selectImage(imageResId: Int) {
        _selectedImage.value = imageResId
    }

}