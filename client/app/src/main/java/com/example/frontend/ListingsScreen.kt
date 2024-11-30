package com.example.frontend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.TopAppBar


@Preview(showBackground = true)
@Composable
fun ListingsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color(94,167,88),
                title = {}
            )

        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            
        }
    }
}

@Composable
fun Listing(listingNum: Int) {
    Row(modifier = Modifier.fillMaxWidth().height(40.dp)) {
        Text(text = "Item no.: $listingNum")
    }
}