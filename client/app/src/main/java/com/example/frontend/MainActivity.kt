package com.example.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.frontend.Screen.ListingsScreen.toScreen
import com.example.frontend.ui.theme.FrontendTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FrontendTheme {
                val navController = rememberNavController()
                val tabs= listOf(
                    NavItem(
                        screen = Screen.ListingsScreen,
                        iconID = R.drawable.baseline_format_list_bulleted_24,
                        title = "Listings"
                    ),
                    NavItem(
                        screen = Screen.ProfileScreen,
                        iconID = R.drawable.baseline_person_24,
                        title = "Profile"
                    )
                )

                val navBackStackEntry = navController.currentBackStackEntryAsState().value
                Scaffold(bottomBar = {
                    NavigationBar {
                        tabs.map { item ->
                            NavigationBarItem(
                                selected = item.screen == navBackStackEntry?.toScreen(),
                                onClick = { navController.navigate(item.screen) },
                                icon = { Icon(painter = painterResource(id = item.iconID), contentDescription = null) },
                                label = { Text(text = item.title) }
                            )
                        }
                    }
                }) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {

                    }
                }
            }
        }
    }
}


data class NavItem(
    val screen: Screen,
    val iconID: Int,
    val title: String
)

@Serializable
sealed class Screen {
    data object ProfileScreen: Screen()
    data object ListingsScreen: Screen()

    fun NavBackStackEntry.toScreen(): Screen? =
        when(destination.route?.substringAfterLast(".")?.substringBefore("/")) {
            "ListingsScreen" -> toRoute<ListingsScreen>()
            "ProfileScreen" -> toRoute<ProfileScreen>()
            else -> null
        }
}