package com.example.frontend

import android.app.AlertDialog
import android.content.Context
import android.widget.EditText
import android.widget.LinearLayout
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
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.frontend.ui.theme.AltBlue
import com.example.frontend.ui.theme.BgWhite
import com.example.frontend.ui.theme.MainBlue


@Composable
fun MyListingsScreen() {
    var tempNum by remember { mutableIntStateOf(0) }

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
//fun AddListingDialog(context: Context, shouldShow: MutableState<Boolean>, onResult: (NewListing) -> Unit) {
//    val layout = LinearLayout(context).apply {
//        orientation = LinearLayout.VERTICAL
//        setPadding(50, 20, 50, 20) // Optional padding
//    }
//
//
//    val nameInputField = EditText(context).apply {
//        hint = "Enter text"
//    }
//    layout.addView(nameInputField)
//
//    val descInputField = EditText(context).apply {
//        hint = "Enter text"
//    }
//    layout.addView(descInputField)
//
//    val locationInputField = EditText(context).apply {
//        hint = "Enter text"
//    }
//    layout.addView(locationInputField)
//
//    val dialog = AlertDialog.Builder(context)
//        .setTitle("Add Listing")
//        .setMessage("Enter details of the listing: ")
//        .setView(layout)
//        .setPositiveButton("OK") { _, _ ->
//            val userInput = NewListing(nameInputField.text.toString(), descInputField.text.toString(), locationInputField.text.toString())
//            onResult(userInput)
//            shouldShow.value = false
//        }
//        .setNegativeButton("Cancel") { dialog, _ ->
//            shouldShow.value = false
//            dialog.dismiss() // Close the dialog
//        }
//        .create()
//
//    if (shouldShow.value) {// Show the dialog
//        dialog.show()
//    }
//}

data class NewListing(
    var name: String,
    val description: String,
    val location: String
)