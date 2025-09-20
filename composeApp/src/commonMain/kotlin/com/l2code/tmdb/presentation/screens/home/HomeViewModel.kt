package com.l2code.tmdb.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.l2code.tmdb.resources.Resources
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class HomeState(
    val isLoading: Boolean = false,
    val catalogResult: CatalogResult? = null,
    val textSearchField: String = "",
)

sealed class CatalogResult(val message: String?) {
    class Success() : CatalogResult(null)
    class Error(resource: String) : CatalogResult(
        Resources.Strings.ERROR_NOT_FOUND
            .replace("{{resource}}", resource)
    )
}

class HomeViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    fun onSearchTextChanged(newText: String) {
        this._uiState.update { it.copy(textSearchField = newText) }
    }
}