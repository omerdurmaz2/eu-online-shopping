package com.euonlineshopping.domain.model.filter

sealed class FiltersUiState {
    data object Loading : FiltersUiState()
    data object Empty : FiltersUiState()
    data class Content(val filters: List<FilterItemUiModel>) : FiltersUiState()
    data class Error(private val message: String) : FiltersUiState()
}
