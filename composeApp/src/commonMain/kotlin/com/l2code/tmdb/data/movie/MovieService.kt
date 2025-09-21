package com.l2code.tmdb.data.movie

import com.l2code.tmdb.data.Pageable
import com.l2code.tmdb.resources.Resources
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

data class MovieListFilters(
    val language: String = Resources.Defaults.DEFAULT_LANGUAGE,
    val page: Int = 1,
)

interface MovieService {
    suspend fun getNowPlayingMovies(filters: MovieListFilters): Pageable<MovieDto>
}

class MovieServiceImpl(private val client: HttpClient) : MovieService {
    override suspend fun getNowPlayingMovies(filters: MovieListFilters): Pageable<MovieDto> {
        val response: Pageable<MovieDto> = client.get(urlString = "movie/now_playing"){
            parameter("language", filters.language)
            parameter("page", filters.page)
        }.body()
        return response
    }
}