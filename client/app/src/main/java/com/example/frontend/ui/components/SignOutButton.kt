package com.example.frontend.ui.components

import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.exceptions.ClearCredentialException
import kotlinx.coroutines.launch

@Composable
fun SignOutButton(signOutFirebase: () -> Unit) {
    val context = LocalContext.current
    val credentialManager = CredentialManager.create(context)
    val coroutineScope = rememberCoroutineScope()
    val onClick: () -> Unit = {
        coroutineScope.launch {
            val request = ClearCredentialStateRequest()
            try {
                credentialManager.clearCredentialState(request)
                Toast.makeText(context, "Signed out", Toast.LENGTH_SHORT).show()
                signOutFirebase()
            } catch (e: ClearCredentialException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    Button(onClick = onClick) {
        Text("Sign out")
    }
}