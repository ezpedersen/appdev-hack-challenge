package com.example.frontend

import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.transform.CircleCropTransformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.frontend.ui.components.GoogleSignInButton
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.example.frontend.ui.LoginViewModel
import com.example.frontend.ui.components.SignOutButton
import coil.compose.AsyncImage
import com.example.frontend.ui.theme.AltBlue
import com.example.frontend.ui.theme.BgWhite


@Preview(showBackground = true)
@Composable
fun ProfileScreen(
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    logOutModel: LoginViewModel = hiltViewModel()
) {

    val profilePicUrl = logOutModel.getProfileIconUrl()

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.loadUser()
    }

    val viewState by viewModel.profileScreenViewState.collectAsState()

    val user = viewState.name
    val netId = viewState.netId
    val bio = viewState.bio
    val offered = viewState.offeredItems
    val want = viewState.wantedItems
    val friends = viewState.friendList
    val profilePic = logOutModel.getProfileIconUrl()

    Column(modifier = Modifier.fillMaxSize().background(BgWhite),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.weight(0.5f)) {
            Column {
                profilePicUrl?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = "Profile Icon",
                        modifier = Modifier
                            .size(100.dp)
                            .background(AltBlue)
                            .clip(CircleShape)
                            .padding(8.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.padding(36.dp))

                Text(text = "Username: $user", fontSize = 24.sp)

                Spacer(modifier = Modifier.padding(16.dp))

                Text(text = "NetId: $netId", fontSize = 20.sp)

                Spacer(modifier = Modifier.padding(10.dp))

                Text(text = "Bio: $bio", fontSize = 20.sp)

                Spacer(modifier = Modifier.padding(36.dp))
                
                SignOutButton(
                    signOutFirebase = {
                        logOutModel.signOut()
                    }
                )

            }
        }
    }
}