package com.euonlineshopping.domain.usecase

import com.euonlineshopping.data.datastore.CartManager
import com.euonlineshopping.domain.model.HomeProductUiModel
import javax.inject.Inject

class ClearCartUseCase @Inject constructor(
    private val cartManager: CartManager
) {
    suspend operator fun invoke() {
        cartManager.clearCart()
    }
}