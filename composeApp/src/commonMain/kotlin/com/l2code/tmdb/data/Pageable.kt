package com.l2code.tmdb.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.Int

@Serializable
data class Pageable<T>(
    val page: Int,
    val results: List<T>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int,
) {
    fun <E> map(mapper: (T) -> E): Pageable<E> = Pageable(
        page = page,
        results = results.map(mapper),
        totalPages = totalPages,
        totalResults = totalResults,
    )
}

