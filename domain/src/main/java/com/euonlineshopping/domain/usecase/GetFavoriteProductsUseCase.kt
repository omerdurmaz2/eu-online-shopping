package com.euonlineshopping.domain.usecase

import com.euonlineshopping.data.datastore.FavoriteProductManager
import com.euonlineshopping.domain.mapper.toUiModel
import com.euonlineshopping.domain.model.HomeProductUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteProductsUseCase @Inject constructor(
    private val favoriteProductManager: FavoriteProductManager,
) {
    operator fun invoke(): Flow<List<HomeProductUiModel>> =
        favoriteProductManager.getFavoriteProducts().map { favoriteProductStrings ->
            favoriteProductStrings.map { product -> product.toUiModel() }
        }
}