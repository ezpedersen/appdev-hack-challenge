package com.example.frontend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.sp
import com.example.frontend.data.Listing
import com.example.frontend.data.User
import com.example.frontend.ui.theme.AltBlue
import com.example.frontend.ui.theme.BgWhite
import com.example.frontend.ui.theme.MainBlue
import java.sql.Date
import java.time.LocalDate


@Composable
fun MyListingsScreen() {
    var tempNum by remember { mutableIntStateOf(0) }

    val proxyUser = User("John", "j001", "Person", listOf(), listOf(), listOf())

    val shouldShowDialog = remember { mutableStateOf(false) }

    var listing: Listing=  Listing("empty", Date.valueOf(LocalDate.now().toString()), "empty", "empty", proxyUser, proxyUser)

    if (shouldShowDialog.value) {
        AddListingDialog(
            shouldShowDialog,
            onSubmit = { name, desc, type ->
                listing = Listing(name, Date.valueOf(LocalDate.now().toString()), desc, type, proxyUser, proxyUser)
                tempNum++
            }
    ) }
    /*
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            CenterAlignedTopAppBar()
        }
    ) { innerPadding ->

    }
    */

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(MainBlue)
            .padding(horizontal = 10.dp)
    ) {
        Text(
            text = "My Listings",
            fontSize = 30.sp,
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .weight(2f)
        )
        IconButton(
            onClick = {
                shouldShowDialog.value = true
              },
            modifier = Modifier
                .size(40.dp)
                .weight(1f)

        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                tint = BgWhite
            )
        }
    }
    LazyColumn (modifier = Modifier.padding(horizontal = 10.dp, vertical = 60.dp)
    ) {
        items(tempNum) { list ->
            ListingTab(listing)
            Spacer(modifier = Modifier.size(10.dp))
        }
    }

}

@Composable
fun ListingTab(listing: Listing) {
    Box(modifier = Modifier
        .height(200.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(AltBlue)
        .fillMaxWidth()
    ) {
        Column{
        Text(text = listing.name)
        Text(text = listing.date.toString())
        Text(text = listing.description)
        Text(text = listing.type)
        Text(text = listing.owner.name)
        Text(text = listing.acceptedBy.name)
            }
    }
}

@Composable
fun AddListingDialog(shouldShowDialog: MutableState<Boolean>, onSubmit: (String, String, String) -> Unit) {
    val name = remember{ mutableStateOf("") }
    val desc = remember { mutableStateOf("") }
    val type = remember { mutableStateOf("") }



    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
            },
            title = { Text(text = "Add Listing") },
            text = {
                // Use a Column to arrange TextFields vertically
                Column {
                    Text(text = "Enter details below:")

                    Spacer(modifier = Modifier.height(8.dp))

                    // First TextField for title input
                    TextField(
                        value = name.value,
                        onValueChange = { name.value = it },
                        label = { Text("Name") },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Second TextField for description input
                    TextField(
                        value = desc.value,
                        onValueChange = { desc.value = it },
                        label = { Text("Description") },
                        singleLine = false,
                        maxLines = 3
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = type.value,
                        onValueChange = { type.value = it },
                        label = { Text("Type") },
                        singleLine = true
                    )

                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Pass the collected inputs to the onSubmit callback
                        onSubmit(name.value, desc.value, type.value)
                        shouldShowDialog.value = false
                    }
                ) {
                    Text(text = "Submit", color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        shouldShowDialog.value = false
                    }
                ) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}


