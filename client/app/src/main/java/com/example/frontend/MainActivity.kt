package com.example.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.frontend.data.AppModule
import com.example.frontend.data.UserRepository
import com.example.frontend.ui.LoginViewModel
import com.example.frontend.ui.theme.AltBlue
import com.example.frontend.ui.theme.FrontendTheme
import com.example.frontend.ui.theme.MainBlue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        //Make something fancy here for the SYSTEM BARS
        window.statusBarColor = AltBlue.toArgb()
        window.navigationBarColor = AltBlue.toArgb()
        setContent {
            FrontendTheme {
                val loginViewModel: LoginViewModel = hiltViewModel()
                val currentScreen by loginViewModel.currentScreen
                val userRepository: UserRepository = UserRepository(AppModule.provideMyApi(AppModule.provideMoshi()))

                when (currentScreen) {
                    "LoginScreen" -> {
                        val navController = rememberNavController()
                        LoginScreen(loginViewModel = loginViewModel, navController = navController)
                    }
                    "OnboardingScreen" -> {
                        val navController = rememberNavController()
                        OnboardingScreen(loginViewModel = loginViewModel, userRepository)
                    }
                    "BaseScreen" -> BaseScreen(userRepository = userRepository, loginViewModel = loginViewModel)
                }

            }
        }
    }
}
