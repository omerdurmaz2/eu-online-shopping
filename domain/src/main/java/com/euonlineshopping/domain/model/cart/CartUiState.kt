package com.euonlineshopping.domain.model.cart

import com.euonlineshopping.domain.model.home.HomeProductUiModel

sealed class CartUiState {
    data object Loading : CartUiState()
    data object Empty : CartUiState()
    data class Content(
        val price: String,
        val discount: String,
        val total: String,
        val products: List<HomeProductUiModel>
    ) : CartUiState()

    data class Error(private val message: String) : CartUiState()
}
