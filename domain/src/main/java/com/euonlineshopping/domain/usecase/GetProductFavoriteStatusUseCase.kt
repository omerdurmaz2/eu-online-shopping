package com.euonlineshopping.domain.usecase

import com.euonlineshopping.data.datastore.FavoriteProductManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductFavoriteStatusUseCase @Inject constructor(
    private val favoriteProductManager: FavoriteProductManager,
) {
    operator fun invoke(productId: Int): Flow<Boolean> =
        favoriteProductManager.isProductFavorite(productId)
}