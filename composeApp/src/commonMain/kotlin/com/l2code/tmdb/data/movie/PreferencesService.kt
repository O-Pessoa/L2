package com.l2code.tmdb.data.movie

expect class PreferencesService {
    suspend fun setFavorites(movies: List<MovieDto>)
    suspend fun getFavorites(): List<MovieDto>
}