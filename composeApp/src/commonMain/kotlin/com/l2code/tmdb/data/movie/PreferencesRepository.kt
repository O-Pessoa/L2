package com.l2code.tmdb.data.movie

import com.l2code.tmdb.entities.Movie

interface PreferencesRepository {
    suspend fun setFavorites(movies: List<Movie>)
    suspend fun getFavorites(): List<Movie>
    suspend fun addFavorite(movie: Movie)
    suspend fun removeFavorite(movie: Movie)
    suspend fun isFavorite(movie: Movie): Boolean
}

class PreferencesRepositoryImpl(private val service: PreferencesService) : PreferencesRepository {
    override suspend fun setFavorites(movies: List<Movie>) {
        service.setFavorites(movies.map(Movie::toDto))
    }

    override suspend fun getFavorites(): List<Movie> = service.getFavorites().map { it.toEntity(true) }

    override suspend fun addFavorite(movie: Movie) {
        val favorites = service.getFavorites()
        if (favorites.any { it.id == movie.id }) {
            return
        }
        service.setFavorites(favorites + movie.toDto())
    }

    override suspend fun removeFavorite(movie: Movie) {
        val favorites = service.getFavorites()
        val newFavorites = favorites.filter { it.id != movie.id }
        service.setFavorites(newFavorites)
    }

    override suspend fun isFavorite(movie: Movie): Boolean {
        val favorites = service.getFavorites()
        return favorites.any { it.id == movie.id }
    }

}