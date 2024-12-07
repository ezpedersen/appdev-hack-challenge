package com.example.frontend

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
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
import com.example.frontend.data.UserRepository
import com.example.frontend.ui.LoginViewModel
import com.example.frontend.ui.theme.AltBlue
import com.example.frontend.ui.theme.MainBlue

sealed class ScreenType(val route: String, val iconIs: ImageVector, val iconNot: ImageVector, val label: String) {
    data object ProfileScreen : ScreenType(
        route = "ProfileScreen",
        iconIs = Icons.Filled.Person,
        iconNot = Icons.Outlined.Person,
        label = "Profile"
    )

    data object ListingsScreen : ScreenType(
        route = "ListingsScreen",
        iconIs = Icons.Filled.Home,
        iconNot = Icons.Outlined.Home,
        label = "Listings"
    )

    data object MyListingsScreen : ScreenType(
        route = "MyListingsScreen",
        iconIs = Icons.Filled.AddCircle,
        iconNot = Icons.Outlined.AddCircleOutline,
        label = "Mine"
    )
}

@Composable
fun BaseScreen(
    loginViewModel : LoginViewModel,
    userRepository : UserRepository
) {
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
                containerColor = AltBlue,
                modifier = Modifier
                    .height(65.dp)
                    .clip(RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                tabs.forEach { screen ->
                    val isSelected = currentDestination?.route == screen.route
                    NavigationBarItem(
                        selected = false,
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
                                imageVector = if (isSelected) screen.iconIs else screen.iconNot,
                                contentDescription = null,
                                tint = if (isSelected) Color.White else Color.LightGray
                            )
                        },
                        label = {
                            Text(
                                text = screen.label,
                                color = if (isSelected) Color.White else Color.LightGray
                            )
                        }
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
                composable(ScreenType.MyListingsScreen.route) { MyListingsScreen(loginViewModel, userRepository) }
                composable(ScreenType.ProfileScreen.route) { ProfileScreen(logOutModel = loginViewModel) }
            }
        }
    }
}