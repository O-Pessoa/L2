package com.l2code.tmdb.entities

import com.l2code.tmdb.AppConfig
import com.l2code.tmdb.data.movie.MovieDto

class Movie(
    val adult: Boolean,
    backdropPath: String,
    val genreIds: List<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val isFavorite: Boolean,
) {
    val backdropPath: String = formatImage(backdropPath)
    val posterPath: String = formatImage(posterPath)

    private fun formatImage(path: String) = "${AppConfig.TMDB_IMAGE_BASE_URL}w400$path"

    fun toDto() = MovieDto(
        this.adult,
        this.backdropPath,
        this.genreIds,
        this.id,
        this.originalLanguage,
        this.originalTitle,
        this.overview,
        this.popularity,
        this.posterPath,
        this.releaseDate,
        this.title,
        this.video,
        this.voteAverage,
        this.voteCount,
    )
}