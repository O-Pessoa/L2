package com.l2code.tmdb.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.l2code.tmdb.presentation.screens.home.HomeScreen

@Composable
fun AppRouter() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Routes.Home()) {
        composable<Routes.Home>{
            HomeScreen(navController)
        }
    }
}
