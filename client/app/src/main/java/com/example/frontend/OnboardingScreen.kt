package com.example.frontend

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.frontend.data.FriendRequest
import com.example.frontend.data.Listing
import com.example.frontend.data.User
import com.example.frontend.ui.LoginViewModel
import com.example.frontend.ui.theme.AltBlue
import com.example.frontend.ui.theme.BgWhite
import com.example.frontend.ui.theme.MainBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val provider = GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs
    )
    val fontName = GoogleFont("Outfit")
    val fontFamilyTitle = FontFamily(
        Font(
            googleFont = fontName,
            fontProvider = provider,
            weight = FontWeight.Medium,
            style = FontStyle.Normal
        )
    )
    val fontFamilyRegular = FontFamily(
        Font(
            googleFont = fontName,
            fontProvider = provider,
            weight = FontWeight.Normal,
            style = FontStyle.Normal
        )
    )

    val uuid : String = loginViewModel.getUId()
    val name : String = loginViewModel.getDisplayName()
    val imageUrl : String = loginViewModel.getProfileIconUrl()
    var netId by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(color = AltBlue),
    ) {
        Text(
            text = "Almost There!",
            fontFamily = fontFamilyTitle,
            fontSize = 45.sp,
            style = TextStyle(
                color = Color.White
            )
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "Please enter in your information.",
            fontFamily = fontFamilyRegular,
            fontSize = 15.sp,
            style = TextStyle(
                color = Color.White
            )
        )
        Spacer(modifier = Modifier.size(30.dp))
        TextField(
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.DarkGray,
                unfocusedTextColor = Color.DarkGray
            ),
            value = netId,
            onValueChange = { newText -> netId = newText },
            label = { Text("Enter your netId!", color = Color.DarkGray) },
            modifier = Modifier.fillMaxWidth(0.8f).background(color = Color.White),
            singleLine = true
        )
        Spacer(modifier = Modifier.size(10.dp))
        TextField(
            colors = TextFieldDefaults.colors(
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                focusedTextColor = Color.DarkGray,
                unfocusedTextColor = Color.DarkGray
            ),
            value = bio,
            onValueChange = { newText -> bio = newText },
            label = { Text("Add something to your bio!", color = Color.DarkGray) },
            modifier = Modifier.fillMaxWidth(0.8f).background(color = Color.White),
            singleLine = false
        )
        Spacer(modifier = Modifier.size(50.dp))
        Button(
            colors = ButtonDefaults.buttonColors(MainBlue),
            onClick =  { loginViewModel.onComplete()},
        ) {
            Text("Finish", color = BgWhite)
        }
    }

}
