package com.example.animespot.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class UserRepository {

    private val auth: FirebaseAuth = Firebase.auth

     fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }
    fun signOut(){
         auth.signOut()
     }

}