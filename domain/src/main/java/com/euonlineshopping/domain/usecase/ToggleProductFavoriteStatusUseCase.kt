package com.euonlineshopping.domain.usecase

import com.euonlineshopping.data.datastore.FavoriteProductManager
import com.euonlineshopping.domain.mapper.toApiModel
import com.euonlineshopping.domain.model.home.HomeProductUiModel
import javax.inject.Inject

class ToggleProductFavoriteStatusUseCase @Inject constructor(
    private val favoriteProductManager: FavoriteProductManager
) {
    suspend operator fun invoke(product: HomeProductUiModel) {
        favoriteProductManager.toggleFavorite(product.toApiModel())
    }
}