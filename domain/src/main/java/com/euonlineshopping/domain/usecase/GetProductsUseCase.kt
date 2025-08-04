package com.euonlineshopping.domain.usecase

import com.euonlineshopping.ProductsUiState
import com.euonlineshopping.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val productsRepository: ProductRepository
) {
    operator fun invoke(sortBy: String?, order: String?): Flow<ProductsUiState> = flow {
        productsRepository.getProducts(sortBy, order).onSuccess { response ->
            if (response.products.isNotEmpty()) {
                emit(ProductsUiState.Content(listOf()))
            } else {
                emit(ProductsUiState.Empty)
            }
        }.onFailure {
            emit(ProductsUiState.Error(""))
        }
    }.onStart { emit(ProductsUiState.Loading) }.flowOn(Dispatchers.IO)
}