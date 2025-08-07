package com.euonlineshopping.domain.usecase

import com.euonlineshopping.data.datastore.FavoriteProductManager
import com.euonlineshopping.domain.mapper.toUiModel
import com.euonlineshopping.domain.model.home.ProductsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetFavoriteProductsUseCase @Inject constructor(
    private val favoriteProductManager: FavoriteProductManager,
) {
    operator fun invoke(): Flow<ProductsUiState> =
        favoriteProductManager.getFavoriteProducts().map { favoriteProductsList ->
            if (favoriteProductsList.isEmpty()) {
                ProductsUiState.Empty
            } else {
                ProductsUiState.Content(
                    favoriteProductsList.map { it.toUiModel() },
                    productCount = favoriteProductsList.size
                )
            }
        }.onStart {
            emit(ProductsUiState.Loading)
        }.flowOn(Dispatchers.IO)
}