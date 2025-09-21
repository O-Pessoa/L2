package com.l2code.tmdb.data.movie

import com.l2code.tmdb.data.Pageable
import com.l2code.tmdb.resources.Resources
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

data class MovieListFilters(
    val query: String? = null,
    val language: String = Resources.Defaults.DEFAULT_LANGUAGE,
    val page: Int = 1,
)

interface MovieService {
    suspend fun getNowPlayingMovies(filters: MovieListFilters): Pageable<MovieDto>
    suspend fun getPopularMovies(filters: MovieListFilters): Pageable<MovieDto>
    suspend fun getTopRatedMovies(filters: MovieListFilters): Pageable<MovieDto>
    suspend fun getUpcomingMovies(filters: MovieListFilters): Pageable<MovieDto>
    suspend fun getSearchMovies(filters: MovieListFilters): Pageable<MovieDto>
}

class MovieServiceImpl(private val client: HttpClient) : MovieService {
    override suspend fun getNowPlayingMovies(filters: MovieListFilters): Pageable<MovieDto> {
        return client.get(urlString = "movie/now_playing"){
            parameter("language", filters.language)
            parameter("page", filters.page)
        }.body()
    }

    override suspend fun getPopularMovies(filters: MovieListFilters): Pageable<MovieDto> {
        return client.get(urlString = "movie/popular"){
            parameter("language", filters.language)
            parameter("page", filters.page)
        }.body()
    }

    override suspend fun getTopRatedMovies(filters: MovieListFilters): Pageable<MovieDto> {
        return client.get(urlString = "movie/top_rated"){
            parameter("language", filters.language)
            parameter("page", filters.page)
        }.body()
    }

    override suspend fun getUpcomingMovies(filters: MovieListFilters): Pageable<MovieDto> {
        return client.get(urlString = "movie/upcoming"){
            parameter("language", filters.language)
            parameter("page", filters.page)
        }.body()
    }

    override suspend fun getSearchMovies(filters: MovieListFilters): Pageable<MovieDto> {
        return client.get(urlString = "search/movie"){
            parameter("language", filters.language)
            parameter("page", filters.page)
            parameter("query", filters.query)
        }.body()
    }
}