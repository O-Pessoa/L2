package com.l2code.tmdb.data.movie

import com.l2code.tmdb.data.Pageable
import com.l2code.tmdb.entities.Movie

interface MovieRepository {
    suspend fun nowPlayingMovies(filters: MovieListFilters): Pageable<Movie>
}

class MovieRepositoryImpl(private val service: MovieService) : MovieRepository {
    override suspend fun nowPlayingMovies(filters: MovieListFilters): Pageable<Movie> {
        return service.getNowPlayingMovies(filters).map(MovieDto::toEntity)
    }
}