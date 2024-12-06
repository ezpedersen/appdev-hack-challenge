package com.example.frontend

import androidx.compose.ui.input.key.Key.Companion.I
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.Listing
import com.example.frontend.data.AppModule
import com.example.frontend.data.GoogleAuthRepository
import com.example.frontend.data.User
import com.example.frontend.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class ProfileScreenViewState(
    val name : String,
    val netId : String,
    val bio : String,
    val wantedItems : List<Listing> = emptyList(),
    val offeredItems : List<Listing> = emptyList(),
    val friendList : List<User> = emptyList()
)

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val googleAuthRepository: GoogleAuthRepository,
    private val UserRepository : UserRepository
) : ViewModel() {
    private var name= ""
    private var netId = ""
    private var bio = ""
    private val wantedFlow = MutableStateFlow(emptyList<Listing>())
    private val offeredFlow = MutableStateFlow(emptyList<Listing>())
    private val friendFlow = MutableStateFlow(emptyList<User>())

    val profileScreenViewState: StateFlow<ProfileScreenViewState> =
        combine(wantedFlow, offeredFlow, friendFlow)
        { wanted: List<Listing>,
          offered: List<Listing>,
          friends: List<User> ->
            createViewState(
                name = name,
                netId = netId,
                bio = bio,
                wanted = wanted,
                offered = offered,
                friend = friends
            )
        }.stateIn(viewModelScope, SharingStarted.Eagerly, ProfileScreenViewState(name,netId,bio))

    init {
        loadUser()
    }

    private fun createViewState(
        name: String,
        netId : String,
        bio : String,
        wanted : List<Listing>,
        offered : List<Listing>,
        friend : List<User>
    ): ProfileScreenViewState {
        return ProfileScreenViewState(
            name = name,
            netId = netId,
            bio = bio,
            wantedItems = wanted,
            offeredItems = offered,
            friendList = friend
        )
    }

    fun loadUser() = viewModelScope.launch {
        try {
            val response = UserRepository.getUserById(netId)
            if (response.isSuccessful) {
                val rawBody = response.body()
                if (rawBody != null) {
                    name = rawBody.name
                    netId = rawBody.netId
                    bio = rawBody.bio
                    val wantedAdd = rawBody?.wantedItems ?: emptyList()
                    val offeredAdd = rawBody?.offeredItems ?: emptyList()
                    val friendsAdd = rawBody?.friendList ?: emptyList()
                    wantedFlow.value = wantedFlow.value + wantedAdd
                    offeredFlow.value = offeredFlow.value + offeredAdd
                    friendFlow.value = friendFlow.value + friendsAdd
                }

            } else {
                println("Error: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            println("Network Error: ${e.message}")
        }
    }
}