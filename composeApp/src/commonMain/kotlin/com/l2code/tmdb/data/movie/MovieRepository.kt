package com.l2code.tmdb.data.movie

import com.l2code.tmdb.data.Pageable
import com.l2code.tmdb.entities.Movie

interface MovieRepository {
    suspend fun nowPlayingMovies(filters: MovieListFilters): Pageable<Movie>
    suspend fun popularMovies(filters: MovieListFilters): Pageable<Movie>
    suspend fun topRatedMovies(filters: MovieListFilters): Pageable<Movie>
    suspend fun upcomingMovies(filters: MovieListFilters): Pageable<Movie>
}

class MovieRepositoryImpl(private val service: MovieService) : MovieRepository {
    override suspend fun nowPlayingMovies(filters: MovieListFilters): Pageable<Movie> {
        return service.getNowPlayingMovies(filters).map(MovieDto::toEntity)
    }

    override suspend fun popularMovies(filters: MovieListFilters): Pageable<Movie> {
        return service.getPopularMovies(filters).map(MovieDto::toEntity)
    }

    override suspend fun topRatedMovies(filters: MovieListFilters): Pageable<Movie> {
        return service.getTopRatedMovies(filters).map(MovieDto::toEntity)
    }

    override suspend fun upcomingMovies(filters: MovieListFilters): Pageable<Movie> {
        return service.getUpcomingMovies(filters).map(MovieDto::toEntity)
    }
}