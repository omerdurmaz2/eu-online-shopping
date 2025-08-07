package com.euonlineshopping.domain.usecase

import com.euonlineshopping.data.datastore.CartManager
import com.euonlineshopping.domain.model.home.HomeProductUiModel
import javax.inject.Inject

class RemoveOneProductFromCartUseCase @Inject constructor(
    private val cartManager: CartManager
) {
    suspend operator fun invoke(product: HomeProductUiModel) {
        cartManager.removeOneProductFromCart(product.id)
    }
}