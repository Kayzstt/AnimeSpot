package com.example.animespot.screens.login

import androidx.lifecycle.ViewModel
import com.example.animespot.repository.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

class AuthViewModel (private val userRepository: UserRepository) : ViewModel() {

     fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return userRepository.createUserWithEmailAndPassword(email, password)
    }

    fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return userRepository.signInWithEmailAndPassword(email, password)
    }

     fun getCurrentUser(): FirebaseUser? {
        return userRepository.getCurrentUser()
    }
     fun signOutUser(){
        return userRepository.signOut()
    }

}