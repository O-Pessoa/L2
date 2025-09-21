package com.l2code.tmdb.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l2code.tmdb.data.Pageable
import com.l2code.tmdb.data.movie.MovieListFilters
import com.l2code.tmdb.data.movie.MovieRepository
import com.l2code.tmdb.entities.Movie
import com.l2code.tmdb.resources.Resources
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RowMovies(
    val title: String,
    val movies: List<Movie> = emptyList(),
    val page: Int = 0,
    val hasNextPage: Boolean = false,
    val load: suspend (filters: MovieListFilters) -> Pageable<Movie>
)

data class HomeState(
    val isLoading: Boolean = false,
    val catalogResult: CatalogResult? = null,
    val textSearchField: String = "",
    val rowsMovies: List<RowMovies> = emptyList(),
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
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                rowsMovies = listOf(
                    RowMovies(
                        title = Resources.Strings.RESOURCE_NOW_PLAYING_MOVIES,
                        load = movieRepository::nowPlayingMovies
                    ),
                    RowMovies(
                        title = Resources.Strings.RESOURCE_POPULAR_MOVIES,
                        load = movieRepository::popularMovies
                    ),
                    RowMovies(
                        title = Resources.Strings.RESOURCE_TOP_RATED_MOVIES,
                        load = movieRepository::topRatedMovies
                    ),
                    RowMovies(
                        title = Resources.Strings.RESOURCE_UPCOMING_MOVIES,
                        load = movieRepository::upcomingMovies
                    ),
                ),
            )
        }

        loadMovies()
    }

    private suspend fun loadRowMovies(rowMovies: RowMovies) {
        if(rowMovies.movies.isNotEmpty() && !rowMovies.hasNextPage) return
        val newMovies = rowMovies.load(MovieListFilters(page = rowMovies.page + 1, query = _uiState.value.textSearchField))
        _uiState.update {
            it.copy(
                rowsMovies = it.rowsMovies.map { row ->
                    if (row == rowMovies) {
                        return@map row.copy(
                            movies = row.movies + newMovies.results,
                            page = newMovies.page,
                            hasNextPage = newMovies.page < newMovies.totalPages
                        )
                    }
                    return@map row
                },
                isLoading = false,
                catalogResult = CatalogResult.Success()
            )
        }
    }

    fun loadMovies(title: String? = null) {
        if (_uiState.value.isLoading) return
        this.viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                for (rowMovies in _uiState.value.rowsMovies) {
                    if (title == null || rowMovies.title == title) {
                        loadRowMovies(rowMovies)
                    }
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

    fun searchMovies() {
        _uiState.update {
            it.copy(
                rowsMovies = listOf(
                    RowMovies(
                        title = Resources.Strings.RESOURCE_SEARCH_MOVIES,
                        page = 0,
                        hasNextPage = false,
                        load = movieRepository::searchMovies,
                    )
                ) + it.rowsMovies,
            )
        }
        loadMovies(Resources.Strings.RESOURCE_SEARCH_MOVIES)
    }

    fun clearError() {
        _uiState.update { it.copy(catalogResult = null) }
    }

    fun onSearchTextChanged(newText: String) {
        this._uiState.update { it.copy(textSearchField = newText) }
    }
}