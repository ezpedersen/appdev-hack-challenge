package com.example.frontend

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.frontend.data.Listing
import com.example.frontend.data.User
import com.example.frontend.ui.LoginViewModel
import com.example.frontend.ui.theme.AltBlue
import com.example.frontend.ui.theme.BgWhite
import com.example.frontend.ui.theme.MainBlue
import java.sql.Date
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MyListingsScreen(logOutModel: LoginViewModel = hiltViewModel()) {

    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )

    val fontName = GoogleFont("Outfit")

    val fontFamily = FontFamily(
        Font(
            googleFont = fontName,
            fontProvider = provider,
            weight = FontWeight.Medium,
            style = FontStyle.Normal
        )
    )

    var tempNum by remember { mutableIntStateOf(0) }

    val proxyUser = User("John", "j001", "---", listOf(), listOf(), listOf())

    val shouldShowDialog = remember { mutableStateOf(false) }

    var listing: Listing=  Listing("empty", Date.valueOf(LocalDate.now().toString()), "empty", "empty", proxyUser, proxyUser)

    if (shouldShowDialog.value) {
        AddListingDialog(
            shouldShowDialog,
            onSubmit = { name, desc, type ->
                val userName = logOutModel.getDisplayName()
                val user = User(userName, "j001", "---", listOf(), listOf(), listOf())
                listing = Listing(name, Date.valueOf(LocalDate.now().toString()), desc, type, user, proxyUser)
                tempNum++
            }
    ) }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AltBlue,
                    scrolledContainerColor = AltBlue
                ),
                title = {
                    Text(
                        text = "My Listings",
                        fontFamily = fontFamily,
                        fontSize = 30.sp,
                        style = TextStyle(
                            fontSize = 24.sp,
                            color = Color.White
                        )
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton( onClick = {
                shouldShowDialog.value = true
            },
                modifier = Modifier
                    .size(40.dp)

            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = BgWhite
                )
            }
        }
    ) { innerPadding ->
        LazyColumn (
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding() - 15.dp,
                    bottom = innerPadding.calculateBottomPadding(),
                )
                .fillMaxSize()
                .background(BgWhite)
                .padding(10.dp)
        ) {
            item {
                Spacer(modifier = Modifier.size(20.dp))
            }
            items(tempNum) { list ->

                ListingTab(listing)
                Spacer(modifier = Modifier.size(10.dp))
            }

        }
    }
}

@Composable
fun ListingTab(listing: Listing) {
    Box(modifier = Modifier
        .height(200.dp)
        .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp), spotColor = Color.Gray)
        .padding(5.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(BgWhite)
        .padding(10.dp)
        .fillMaxWidth()
    ) {
        Column{
            Text(text = listing.name, color = Color.DarkGray)
            Text(text = listing.date.toString(), color = Color.DarkGray)
            Text(text = listing.description, color = Color.DarkGray)
            Text(text = listing.type, color = Color.DarkGray)
            Text(text = listing.owner.name, color = Color.DarkGray)
            Text(text = listing.acceptedBy.name, color = Color.DarkGray)
        }
    }
}

@Composable
fun AddListingDialog(shouldShowDialog: MutableState<Boolean>, onSubmit: (String, String, String) -> Unit) {
    val name = remember{ mutableStateOf("") }
    val desc = remember { mutableStateOf("") }
    val type = remember { mutableStateOf("") }

    var imageUri by remember { mutableStateOf<Uri?>(null)}

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri -> imageUri = uri}
    )



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

                    Button(
                        onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    ) {
                        Text(text = "Add Picture")
                    }
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


