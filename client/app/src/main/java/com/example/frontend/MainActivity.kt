package com.example.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.frontend.ui.theme.FrontendTheme
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.frontend.ui.theme.MainBlue
import dagger.hilt.android.AndroidEntryPoint


sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    data object ProfileScreen: Screen(
        route = "ProfileScreen",
        icon = Icons.Outlined.Person,
        label = "Profile")
    data object ListingsScreen: Screen(
        route = "ListingsScreen",
        icon = Icons.Outlined.Home,
        label = "Listings"
    )
    data object MyListingsScreen : Screen(
        route = "MyListingsScreen",
        icon = Icons.Outlined.AddCircleOutline,
        label = "My"
    )
}
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        //Make something fancy here for the SYSTEM BARS
        window.statusBarColor = MainBlue.toArgb()
        window.navigationBarColor = MainBlue.toArgb()
        setContent {
            FrontendTheme {
                val navController = rememberNavController()
                val tabs= listOf(
                    Screen.ListingsScreen,
                    Screen.MyListingsScreen,
                    Screen.ProfileScreen
                )
                Scaffold(
                    modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
                    bottomBar = {
                        NavigationBar (
                            containerColor = MainBlue,
                            modifier = Modifier.clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
                        ) {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            tabs.forEach { screen ->
                                NavigationBarItem(
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = { Icon(imageVector = screen.icon, contentDescription = null) },
                                    label = { Text(text = screen.label) }
                                )
                            }
                        }
                    }) { innerPadding ->
                    Box(modifier = Modifier
                        .padding(
                            top = innerPadding.calculateTopPadding(),
                            bottom = innerPadding.calculateBottomPadding() - 15.dp)
                    ) {
                        NavHost(navController = navController,
                            startDestination = Screen.ListingsScreen.route) {
                            composable(Screen.ListingsScreen.route) { ListingsScreen() }
                            composable(Screen.MyListingsScreen.route) { MyListingsScreen() }
                            composable(Screen.ProfileScreen.route) { ProfileScreen() }
                        }
                    }
                }
            }
        }
    }
}