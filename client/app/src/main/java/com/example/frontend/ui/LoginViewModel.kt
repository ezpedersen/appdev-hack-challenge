package com.example.frontend.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.frontend.BaseScreen
import com.example.frontend.LoginScreen
import com.example.frontend.data.GoogleAuthRepository
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import androidx.compose.runtime.State

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val googleAuthRepository: GoogleAuthRepository,
) : ViewModel() {

    private val curScreen = mutableStateOf("LoginScreen")
    val currentScreen: State<String> = curScreen
    var token : String = ""

    fun onSignIn(googleIdTokenCredential: GoogleIdTokenCredential) {
        googleAuthRepository.addToFirebaseAuth(googleIdTokenCredential) { success ->
            if (success) {
                curScreen.value = "BaseScreen"
                token = googleIdTokenCredential.idToken
            }
        }


    }

    fun signOut() {
        googleAuthRepository.signOutFirebaseAuth()
        curScreen.value = "LoginScreen"
    }

    init {
        curScreen.value = if (googleAuthRepository.auth.currentUser != null) {
            "BaseScreen"
        } else {
            "LoginScreen"
        }
    }

}