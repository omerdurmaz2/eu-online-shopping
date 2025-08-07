package com.euonlineshopping.domain.model.home

sealed class ProductsUiState {
    data object Loading : ProductsUiState()
    data object Empty : ProductsUiState()
    data class Content(val products: List<HomeProductUiModel>, val productCount: Int) :
        ProductsUiState()

    data class Error(private val message: String) : ProductsUiState()
}
