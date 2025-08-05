package com.euonlineshopping.domain.usecase

import com.euonlineshopping.data.datastore.CartManager
import com.euonlineshopping.domain.mapper.toUiModel
import com.euonlineshopping.domain.model.CartUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

class GetCartProductsUseCase @Inject constructor(
    private val cartManager: CartManager,
) {
    operator fun invoke(): Flow<CartUiState> {
        return cartManager.getCartProducts()
            .map { productList ->
                if (productList.isEmpty()) {
                    CartUiState.Empty
                } else {
                    val groupedProducts = productList.groupBy { it.id }

                    val cartProducts = groupedProducts.map { (id, products) ->
                        val firstProduct = products.first()
                        firstProduct.toUiModel()
                    }

                    val subtotal = cartProducts.sumOf { it.price * it.count }
                    val discount = 0.0
                    val total = subtotal - discount

                    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("tr", "TR"))

                    CartUiState.Content(
                        price = currencyFormatter.format(subtotal),
                        discount = currencyFormatter.format(discount),
                        total = currencyFormatter.format(total),
                        products = cartProducts
                    )
                }
            }
            .onStart {
                emit(CartUiState.Loading)
            }
            .flowOn(Dispatchers.IO)
    }
}