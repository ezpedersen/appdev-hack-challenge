package com.example.frontend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp


@Composable
fun MyListingsScreen() {
    var tempNum by remember { mutableIntStateOf(0) }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = "My Listings",
            fontSize = 30.sp,
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .weight(2f)
        )
        IconButton(
            onClick = { tempNum++ },
            modifier = Modifier
                .size(40.dp)
                .weight(1f)

        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                tint = Color.LightGray
            )
        }
    }
    LazyColumn (modifier = Modifier.padding(60.dp)
    ) {
        items(tempNum) { listing ->
            Listing()
            Spacer(modifier = Modifier.size(10.dp))
        }
    }

}

@Composable
fun Listing() {
    Box(modifier = Modifier
        .background(Color.Green)
        .size(100.dp)
    ) {

    }
}