package com.l2code.tmdb

import androidx.compose.runtime.Composable
import com.l2code.tmdb.presentation.navigation.AppRouter


@Composable
fun App() {
    AppTheme {
        AppRouter()
    }
}