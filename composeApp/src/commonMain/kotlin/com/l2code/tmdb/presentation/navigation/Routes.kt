package com.l2code.tmdb.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable class Home : Routes()
}