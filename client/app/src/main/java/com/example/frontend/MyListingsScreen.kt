package com.example.frontend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.frontend.ui.theme.AltBlue
import com.example.frontend.ui.theme.BgWhite
import com.example.frontend.ui.theme.Gray
import com.example.frontend.ui.theme.MainBlue


@Composable
fun MyListingsScreen() {
    var tempNum by remember { mutableIntStateOf(0) }

    val shouldShowDialogBox = remember { mutableStateOf(false) }

    if (shouldShowDialogBox.value) {
//        AddDialogBox() { }
    }
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
                tempNum++
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
        items(tempNum) { listing ->
            Listing()
            Spacer(modifier = Modifier.size(10.dp))
        }
    }

}

@Composable
fun Listing() {
    Box(modifier = Modifier
        .height(200.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(AltBlue)
        .fillMaxWidth()
    ) {

    }
}

//@Composable
//fun AddDialogBox(shouldShowDialog: MutableState<Boolean>
//): NewListing {
//
//    val itemName by remember { mutableStateOf(String)}
//    val itemDesc by remember { mutableStateOf(String)}
//
//    if (shouldShowDialog.value) {
//        Dialog(onDismissRequest = { shouldShowDialog.value = false }) {
//            // Draw a rectangle shape with rounded corners inside the dialog
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(375.dp)
//                    .padding(16.dp),
//                shape = RoundedCornerShape(16.dp),
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize().padding(10.dp),
//                    verticalArrangement = Arrangement.Center,
//                ) {
//                    Text(
//                        text = "Add image",
//                        modifier = Modifier.padding(16.dp),
//                    )
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        horizontalArrangement = Arrangement.Center,
//                    ) {
//                        TextButton(
//                            onClick = {shouldShowDialog.value = false},
//                            modifier = Modifier.padding(8.dp),
//                        ) {
//                            Text("Dismiss")
//                        }
//                        TextButton(
//                            onClick = {
//                                shouldShowDialog.value = false
//                                val listing: NewListing = NewListing(itemName.toString(), itemDesc.toString())
//                                return listing
//                                  },
//                            modifier = Modifier.padding(8.dp),
//                        ) {
//                            Text("Confirm")
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//data class NewListing(
//    var name: String,
//    val description: String
//
//)