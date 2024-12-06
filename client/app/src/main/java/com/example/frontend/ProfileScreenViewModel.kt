package com.example.frontend

import androidx.compose.ui.input.key.Key.Companion.I
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.RetroFitUserHelper
import com.example.frontend.data.UserRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileScreenViewState(
    private val name : String = "",
    private val netId : String = "",
    private val bio : String = "",
    private val wantedItems : String = ""
)

class ProfileScreenViewModel @Inject constructor(
    private val UserRepository : UserRepository,
) : ViewModel() {

    val profileScreenViewState: StateFlow<ProfileScreenViewState>

    init {
        loadUser()
    }

    fun loadUser() = viewModelScope.launch {
        try {
            val response = UserRepository.getUserById(netId)
            if (response.isSuccessful) {
                val rawBody = response.body()
            } else {
                println("Error: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            println("Network Error: ${e.message}")
        }
    }
}