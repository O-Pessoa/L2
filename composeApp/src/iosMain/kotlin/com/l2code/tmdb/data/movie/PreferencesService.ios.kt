package com.l2code.tmdb.data.movie

import kotlinx.serialization.json.Json
import platform.Foundation.NSUserDefaults

actual class PreferencesService() {
    private val defaults = NSUserDefaults.standardUserDefaults
    private val FAVORITES_KEY = "favorites"
    private val WATCHLIST_KEY = "watchlist"
    private val json = Json {
        encodeDefaults = false
        explicitNulls = false
        ignoreUnknownKeys = true
    }

    actual suspend fun setFavorites(movies: List<MovieDto>) {
        val jsonString = json.encodeToString(movies)
        defaults.setObject(jsonString,FAVORITES_KEY)
    }

    actual suspend fun getFavorites(): List<MovieDto> {
        val jsonString = defaults.stringForKey(FAVORITES_KEY) ?: "[]"
        return json.decodeFromString(jsonString)
    }

    actual suspend fun setWatchlist(movies: List<MovieDto>) {
        val jsonString = json.encodeToString(movies)
        defaults.setObject(jsonString,WATCHLIST_KEY)
    }

    actual suspend fun getWatchlist(): List<MovieDto> {
        val jsonString = defaults.stringForKey(WATCHLIST_KEY) ?: "[]"
        return json.decodeFromString(jsonString)
    }
}