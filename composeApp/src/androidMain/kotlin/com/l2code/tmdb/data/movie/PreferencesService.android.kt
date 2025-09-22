package com.l2code.tmdb.data.movie

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json


actual class PreferencesService(private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "preferences")
    private val FAVORITES_KEY = stringPreferencesKey("favorites")
    private val WATCHLIST_KEY = stringPreferencesKey("watchlist")
    private val json = Json {
        encodeDefaults = false
        explicitNulls = false
        ignoreUnknownKeys = true
    }

    actual suspend fun setFavorites(movies: List<MovieDto>) {
        context.dataStore.edit { preferences ->
            preferences[FAVORITES_KEY] = json.encodeToString(movies)
        }
    }

    actual suspend fun getFavorites(): List<MovieDto> {
        val preferences = context.dataStore.data.first()
        val jsonString = preferences[FAVORITES_KEY]
        return jsonString?.let { json.decodeFromString(it) } ?: emptyList()
    }

    actual suspend fun setWatchlist(movies: List<MovieDto>) {
        context.dataStore.edit { preferences ->
            preferences[WATCHLIST_KEY] = json.encodeToString(movies)
        }
    }

    actual suspend fun getWatchlist(): List<MovieDto> {
        val preferences = context.dataStore.data.first()
        val jsonString = preferences[WATCHLIST_KEY]
        return jsonString?.let { json.decodeFromString(it) } ?: emptyList()
    }
}