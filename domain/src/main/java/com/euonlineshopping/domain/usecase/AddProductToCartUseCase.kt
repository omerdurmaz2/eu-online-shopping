package com.euonlineshopping.domain.usecase

import com.euonlineshopping.data.datastore.CartManager
import com.euonlineshopping.data.model.Product
import com.euonlineshopping.domain.mapper.toApiModel
import com.euonlineshopping.domain.model.HomeProductUiModel
import javax.inject.Inject

class AddProductToCartUseCase @Inject constructor(
    private val cartManager: CartManager
) {
    suspend operator fun invoke(product: HomeProductUiModel) {
        cartManager.addProductToCart(product.toApiModel())
    }
}