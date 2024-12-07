package com.example.frontend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import com.example.frontend.ui.theme.AltBlue
import com.example.frontend.ui.theme.BgWhite
import com.example.frontend.ui.theme.MainBlue

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ListingsScreen() {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
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
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .background(AltBlue)
            .systemBarsPadding(),
        topBar = {
            MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AltBlue,
                    scrolledContainerColor = AltBlue
                ),
                title = {
                    Text(
                        text = "Listings",
                        fontFamily = fontFamily,
                        fontSize = 30.sp,
                        style = TextStyle(
                            fontSize = 24.sp,
                            color = Color.White
                        )
                    )
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier
            .padding(
                top = innerPadding.calculateTopPadding() - 15.dp,
                bottom = innerPadding.calculateBottomPadding(),
            )
            .fillMaxWidth()
            .background(BgWhite)
            .padding(10.dp)
        ) {
            item {
                Spacer(modifier = Modifier.size(20.dp))
            }
            items(100) { listing ->
                Listing(listing)
                Spacer(modifier = Modifier.size(10.dp))
            }
            item {
                Spacer(modifier = Modifier.size(20.dp))
            }
        }
    }
}

@Composable
fun Listing(index: Int) {
    Box(modifier = Modifier
        .height(200.dp)
        .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp), spotColor = Color.Gray)
        .padding(5.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(BgWhite)
        .padding(10.dp)
        .fillMaxWidth()

    ) {
        Text(
            text = "Listing no: $index",
            color = Color.DarkGray
        )
    }
}