package com.l2code.tmdb.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l2code.tmdb.data.movie.MovieListFilters
import com.l2code.tmdb.data.movie.MovieRepository
import com.l2code.tmdb.entities.Movie
import com.l2code.tmdb.resources.Resources
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeState(
    val isLoading: Boolean = false,
    val catalogResult: CatalogResult? = null,
    val textSearchField: String = "",
    val movies: List<Movie> = emptyList(),
    val page: Int = 0,
    val hasNextPage: Boolean = false,
)

sealed class CatalogResult(val message: String?) {
    class Success() : CatalogResult(null)
    class Error(resource: String) : CatalogResult(
        Resources.Strings.ERROR_NOT_FOUND
            .replace("{{resource}}", resource)
    )
}

class HomeViewModel(
    private val movieRepository: MovieRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        this.viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val result = movieRepository.nowPlayingMovies(MovieListFilters(page = _uiState.value.page + 1))
                _uiState.update {
                    it.copy(
                        page = result.page,
                        hasNextPage = result.page < result.totalPages,
                        movies = result.results,
                        catalogResult = CatalogResult.Success(),
                        isLoading = false,
                    )
                }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        catalogResult = CatalogResult.Error(Resources.Strings.RESOURCE_NOW_PLAYING_MOVIES),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(catalogResult = null) }
    }

    fun onSearchTextChanged(newText: String) {
        this._uiState.update { it.copy(textSearchField = newText) }
    }
}