package com.l2code.tmdb.data.movie

import com.l2code.tmdb.entities.Movie

interface PreferencesRepository {
    suspend fun setFavorites(movies: List<Movie>)
    suspend fun getFavorites(): List<Movie>
    suspend fun addFavorite(movie: Movie)
    suspend fun removeFavorite(movie: Movie)
    suspend fun isFavorite(movie: Movie): Boolean

    suspend fun setWatchlist(movies: List<Movie>)
    suspend fun getWatchlist(): List<Movie>
    suspend fun addToWatchlist(movie: Movie)
    suspend fun removeFromWatchlist(movie: Movie)
    suspend fun isInWatchlist(movie: Movie): Boolean
}

class PreferencesRepositoryImpl(private val service: PreferencesService) : PreferencesRepository {
    override suspend fun setFavorites(movies: List<Movie>) {
        service.setFavorites(movies.map(Movie::toDto))
    }

    override suspend fun getFavorites(): List<Movie> {
        val watchlist = service.getWatchlist()
        return service.getFavorites().map { movie ->
            movie.toEntity(
                true,
                watchlist.any { it.id == movie.id }
            )
        }
    }

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

    override suspend fun setWatchlist(movies: List<Movie>) {
        service.setWatchlist(movies.map(Movie::toDto))
    }

    override suspend fun getWatchlist(): List<Movie> {
        val favorites = service.getFavorites()
        return service.getWatchlist().map { movie ->
            movie.toEntity(
                favorites.any { it.id == movie.id },
                true
            )
        }
    }

    override suspend fun addToWatchlist(movie: Movie) {
        val watchlist = service.getWatchlist()
        if (watchlist.any { it.id == movie.id }) {
            return
        }
        service.setWatchlist(watchlist + movie.toDto())
    }

    override suspend fun removeFromWatchlist(movie: Movie) {
        val watchlist = service.getWatchlist()
        val newWatchlist = watchlist.filter { it.id != movie.id }
        service.setWatchlist(newWatchlist)
    }

    override suspend fun isInWatchlist(movie: Movie): Boolean {
        val watchlist = service.getWatchlist()
        return watchlist.any { it.id == movie.id }
    }

}