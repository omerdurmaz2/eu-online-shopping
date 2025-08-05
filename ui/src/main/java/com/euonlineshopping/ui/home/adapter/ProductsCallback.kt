package com.euonlineshopping.ui.home.adapter

import com.euonlineshopping.domain.model.HomeProductUiModel

interface ProductsCallback {
    fun navigateToDetail(product: HomeProductUiModel)
    fun toggleFavorite(product: HomeProductUiModel) {}
    fun addToCart(product: HomeProductUiModel) {}
}