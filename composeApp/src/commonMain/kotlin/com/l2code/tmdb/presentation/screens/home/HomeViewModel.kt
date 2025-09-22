package com.l2code.tmdb.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.l2code.tmdb.data.Pageable
import com.l2code.tmdb.data.movie.MovieListFilters
import com.l2code.tmdb.data.movie.MovieRepository
import com.l2code.tmdb.data.movie.PreferencesRepository
import com.l2code.tmdb.entities.Movie
import com.l2code.tmdb.resources.Resources
import kotlinx.coroutines.delay
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
    private val preferencesRepository: PreferencesRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                rowsMovies = listOf(
                    RowMovies(
                        title = Resources.Strings.RESOURCE_FAVORITES_MOVIES,
                        load = this::getFavorites
                    ),
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

    fun loadRowMovies(rowMovies: RowMovies, isRefresh: Boolean = false) {
        if (rowMovies.movies.isNotEmpty() && !rowMovies.hasNextPage && !isRefresh) return
        this.viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val newMovies = rowMovies.load(
                    MovieListFilters(
                        page = rowMovies.page + 1,
                        query = _uiState.value.textSearchField
                    )
                )
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        catalogResult = CatalogResult.Success(),
                        rowsMovies = it.rowsMovies.map { row ->
                            if (row == rowMovies) {
                                return@map row.copy(
                                    movies = if(isRefresh) newMovies.results else row.movies + newMovies.results,
                                    page = newMovies.page,
                                    hasNextPage = newMovies.page < newMovies.totalPages
                                )
                            }
                            return@map row
                        },
                    )
                }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        catalogResult = CatalogResult.Error(rowMovies.title),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun loadMovies() {
        if (_uiState.value.isLoading) return
        for (rowMovies in _uiState.value.rowsMovies) {
            loadRowMovies(rowMovies)
        }
    }

    suspend fun getFavorites(filters: MovieListFilters?): Pageable<Movie> {
        val movies = preferencesRepository.getFavorites()
        return Pageable<Movie>(
            page = 1,
            totalPages = 1,
            totalResults = movies.size,
            results = movies
        )
    }

    fun searchMovies() {
        val oldSearchMovies = _uiState.value.rowsMovies.find { it.title == Resources.Strings.RESOURCE_SEARCH_MOVIES }
        if (oldSearchMovies != null) {
            _uiState.update { state ->
                state.copy(
                    rowsMovies = state.rowsMovies.filter { oldSearchMovies.title != it.title }
                )
            }
        }
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
        val rowMovies = _uiState.value.rowsMovies.find { it.title == Resources.Strings.RESOURCE_SEARCH_MOVIES }
        if (rowMovies == null) return
        this.viewModelScope.launch {
            val exitAnimationDuration = 300L
            delay(exitAnimationDuration)
            loadRowMovies(rowMovies)
        }
    }

    fun clearError() {
        _uiState.update { it.copy(catalogResult = null) }
    }

    fun onSearchTextChanged(newText: String) {
        this._uiState.update { it.copy(textSearchField = newText) }
    }

    fun addFavorite(movie: Movie) {
        viewModelScope.launch {
            preferencesRepository.addFavorite(movie)
            val favoritesRow =
                _uiState.value.rowsMovies.find { it.title == Resources.Strings.RESOURCE_FAVORITES_MOVIES }
            if (favoritesRow != null) {
                loadRowMovies(favoritesRow, true)
            }
        }
    }

    fun removeFavorite(movie: Movie) {
        viewModelScope.launch {
            preferencesRepository.removeFavorite(movie)
            val favoritesRow =
                _uiState.value.rowsMovies.find { it.title == Resources.Strings.RESOURCE_FAVORITES_MOVIES }
            if (favoritesRow != null) {
                loadRowMovies(favoritesRow, true)
            }
        }
    }
}