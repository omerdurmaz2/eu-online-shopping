package com.euonlineshopping

import com.euonlineshopping.domain.model.HomeProductUiModel

sealed class ProductsUiState {
    data object Loading : ProductsUiState()
    data object Empty : ProductsUiState()
    data class Content(private val products: List<HomeProductUiModel>) : ProductsUiState()
    data class Error(private val message: String) : ProductsUiState()
}
