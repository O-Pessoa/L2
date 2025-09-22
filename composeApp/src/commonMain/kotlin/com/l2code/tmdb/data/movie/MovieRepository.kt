package com.l2code.tmdb.data.movie

import com.l2code.tmdb.data.Pageable
import com.l2code.tmdb.entities.Movie

interface MovieRepository {
    suspend fun nowPlayingMovies(filters: MovieListFilters): Pageable<Movie>
    suspend fun popularMovies(filters: MovieListFilters): Pageable<Movie>
    suspend fun topRatedMovies(filters: MovieListFilters): Pageable<Movie>
    suspend fun upcomingMovies(filters: MovieListFilters): Pageable<Movie>
    suspend fun searchMovies(filters: MovieListFilters): Pageable<Movie>
}

class MovieRepositoryImpl(
    private val service: MovieService,
    private val preferencesService: PreferencesService
) : MovieRepository {

    override suspend fun nowPlayingMovies(filters: MovieListFilters): Pageable<Movie> {
        val favorites = preferencesService.getFavorites()
        return service.getNowPlayingMovies(filters).map {
            it.toEntity(favorites.contains(it))
        }
    }

    override suspend fun popularMovies(filters: MovieListFilters): Pageable<Movie> {
        val favorites = preferencesService.getFavorites()
        return service.getPopularMovies(filters).map {
            it.toEntity(favorites.contains(it))
        }
    }

    override suspend fun topRatedMovies(filters: MovieListFilters): Pageable<Movie> {
        val favorites = preferencesService.getFavorites()
        return service.getTopRatedMovies(filters).map {
            it.toEntity(favorites.contains(it))
        }
    }

    override suspend fun upcomingMovies(filters: MovieListFilters): Pageable<Movie> {
        val favorites = preferencesService.getFavorites()
        return service.getUpcomingMovies(filters).map {
            it.toEntity(favorites.contains(it))
        }
    }

    override suspend fun searchMovies(filters: MovieListFilters): Pageable<Movie> {
        val favorites = preferencesService.getFavorites()
        return service.getSearchMovies(filters).map {
            it.toEntity(favorites.contains(it))
        }
    }
}