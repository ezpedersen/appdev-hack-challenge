package com.example.frontend

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.frontend.ui.LoginViewModel

sealed class ScreenType(val route: String, val icon: ImageVector, val label: String) {
    data object ProfileScreen : ScreenType(
        route = "ProfileScreen",
        icon = Icons.Outlined.Person,
        label = "Profile"
    )

    data object ListingsScreen : ScreenType(
        route = "ListingsScreen",
        icon = Icons.Outlined.Home,
        label = "Listings"
    )

    data object MyListingsScreen : ScreenType(
        route = "MyListingsScreen",
        icon = Icons.Outlined.AddCircleOutline,
        label = "My"
    )
}

@Composable
fun BaseScreen(loginViewModel : LoginViewModel) {
    val navController = rememberNavController()
    val tabs = listOf(
        ScreenType.ListingsScreen,
        ScreenType.MyListingsScreen,
        ScreenType.ProfileScreen
    )
    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        bottomBar = {
            NavigationBar(
                containerColor = Color(62, 180, 137),
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
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = null
                            )
                        },
                        label = { Text(text = screen.label) }
                    )
                }
            }
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding() - 15.dp
                )
        ) {
            NavHost(
                navController = navController,
                startDestination = ScreenType.ListingsScreen.route
            ) {
                composable(ScreenType.ListingsScreen.route) { ListingsScreen() }
                composable(ScreenType.MyListingsScreen.route) { MyListingsScreen() }
                composable(ScreenType.ProfileScreen.route) { ProfileScreen(logOutModel = loginViewModel) }
            }
        }
    }
}