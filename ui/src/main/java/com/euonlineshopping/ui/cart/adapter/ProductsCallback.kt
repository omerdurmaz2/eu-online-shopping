package com.euonlineshopping.ui.cart.adapter

import com.euonlineshopping.domain.model.HomeProductUiModel

interface CartProductsCallback {
    fun increaseCount(product: HomeProductUiModel)
    fun decreaseCount(product: HomeProductUiModel)
    fun removeProduct(product: HomeProductUiModel)
    fun navigateToDetail(product: HomeProductUiModel)
}