package com.example.frontend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.frontend.ui.LoginViewModel
import com.example.frontend.ui.components.GoogleSignInButton

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GoogleSignInButton { googleIdTokenCredential ->
                loginViewModel.onSignIn(googleIdTokenCredential)
            }
        }
    }
}
