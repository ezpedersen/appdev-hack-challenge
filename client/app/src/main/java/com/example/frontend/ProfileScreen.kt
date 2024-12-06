package com.example.frontend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.frontend.data.RetroFitUserHelper
import com.example.frontend.data.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@Preview(showBackground = true)
@Composable
fun ProfileScreen(viewModel: ProfileScreenViewModel = hiltViewModel()) {

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        snapshotFlow { }.onEach {
            viewModel.loadUser()
        }.launchIn(coroutineScope)
    }

    val viewState by viewModel.profileScreenViewState.collectAsState()

    val user = viewState.name
    val netId = viewState.netId
    val bio = viewState.bio
    val offered = viewState.offeredItems
    val want = viewState.wantedItems
    val friends = viewState.friendList

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.weight(0.5f)) {
            Column {
                Text(text = "Username: $user")
                Spacer(modifier = Modifier.padding(16.dp))
                Text(text = "NetId: $netId")
                Text(text = "Bio: $bio")
            }
        }
    }
}