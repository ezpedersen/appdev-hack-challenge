package com.example.frontend.data

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleAuthRepository @Inject constructor() {

    val auth = Firebase.auth

    fun addToFirebaseAuth(
        googleIdTokenCredential: GoogleIdTokenCredential,
        onComplete: (Boolean) -> Unit
    ) {
        val firebaseCredential =
            GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
        auth.signInWithCredential(firebaseCredential)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    }

    fun getUserProfilePicUrl(): String? {
        val user = auth.currentUser
        return user?.photoUrl?.toString()
    }

    fun signOutFirebaseAuth() {
        auth.signOut()
    }
}