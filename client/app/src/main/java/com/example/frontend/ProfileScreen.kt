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
    //This was for a test
    /*
    var result by remember { mutableStateOf("Loading...") }
    // Use LaunchedEffect to trigger the coroutine
    LaunchedEffect(Unit) {
        try {
            val response = RetroFitUserHelper.api.getTest()
            if (response.isSuccessful) {
                val rawBody = response.body()
                result = rawBody.toString()
                println("Raw Body: $result")
            } else {
                println("Error: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            println("Network Error: ${e.message}")
        }
    }
    */

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        snapshotFlow { }.onEach {
            viewModel.loadUser()
        }.launchIn(coroutineScope)
    }

    val viewState by viewModel.

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.weight(0.5f)) {
            Column {
                Text(text = "Username: ")
                Spacer(modifier = Modifier.padding(16.dp))
                Text(text = "NetId: ")
                Text(text = "result")
            }
        }
    }
}